import csv
import numpy as np
import pandas as pd
from keras.preprocessing.text import Tokenizer
from keras import preprocessing

import plotly.plotly as py
import plotly.tools as tls

import matplotlib.pyplot as plt
import datetime
from sklearn.feature_extraction.text import CountVectorizer
from keras.layers import Flatten, Dense
from keras.layers import Embedding
from keras import utils
import nltk
#nltk.download()
from nltk.stem.wordnet import WordNetLemmatizer
from nltk import word_tokenize
from nltk.stem import PorterStemmer
from nltk.stem.wordnet import WordNetLemmatizer
from nltk import pos_tag
import unidecode
from nltk.corpus import stopwords
import string
import math
from math import log
import operator
import matplotlib.gridspec as gridspec
import re
from sklearn.model_selection import StratifiedKFold
from keras.models import Sequential
from keras import layers
from keras.optimizers import RMSprop
from keras.layers import SimpleRNN


def get_x_and_y(data_csv):
    x = []
    y = []
    line = 0
    with open(data_csv, 'r') as file:
        reader = csv.reader(file, quotechar='"')
        for row in reader:
            if '' == row[1]:
                y.append([float(0)])
            else:
                y.append([float(row[1])])
            _as_string = unidecode.unidecode(row[0].decode('ascii', 'ignore'))
            x.append([_as_string])

            line = line + 1

    print('processed {} lines'.format(line))
    return np.asarray(x), np.asarray(y)


def get_x_and_y_stocks(data_csv, _from, _to):
    date_pattern = '%Y-%m-%d'
    rows = []
    header = True
    with open(data_csv, 'r') as file:
        reader = csv.reader(file, quotechar='"')
        for row in reader:
            if header:
                header = False
                continue
            ts = datetime.datetime.strptime(str(row[0]), date_pattern)
            if ts.year < _from or ts.year > _to:
                continue
            rows.append(row)

    dates = []
    data = np.zeros((len(rows), 5))
    for i, row in enumerate(rows):
        values = [float(x) for x in row[1:6]]
        data[i, :] = values
        dates.append([row[0]])

    print('processed {} lines'.format(i))
    return np.array(dates), data

def vectorize2(x, word_dict_len, sentence_words_max):

    sentences = []
    words = {}
    word_count = {}

    for row_index, row in enumerate(x):
        sentence = [0] * sentence_words_max
        word_index = 0
        for word in row:
            if word in string.punctuation:
                continue

            if word not in words.keys():
                words_index = len(words.keys())+1
                words[word] = words_index
                sentence[word_index] = words_index
                word_count[words_index] = 1
            else:
                sentence[word_index] = words[word]
                word_count[words[word]] += 1

            word_index += 1
            if sentence_words_max == word_index:
                break

        sentences.append(sentence)

    word_dict = {}
    for word, index in words.iteritems():
        word_dict[index] = word

    count = 0
    new_word_dict = {}
    for key, value in sorted(word_count.iteritems(), key=lambda (k, v): (v, k), reverse=True):
        new_word_dict[word_dict[key]] = key
        count += 1
        if word_dict_len == count:
            break

    for row_index, row in enumerate(sentences):
        for word_index, word in enumerate(row):
            if word not in new_word_dict.values() and word != 0:
                sentences[row_index][word_index] = 0

    return sentences, new_word_dict


def build_word_dict_and_frequency_by_sorted_frequency(x, filter_punctuation=True, descendent=True):
    words = {}
    word_freq = {}
    for row_index, row in enumerate(x):
        for word in row:
            if filter_punctuation and (word.strip() in string.punctuation or re.match('\'.$', word.strip()) is not None):
                continue
            if word not in words.keys():
                words[word] = len(words.keys())+1
                word_freq[word] = 1
            else:
                word_freq[word] += 1

    words_stats = []
    for word, frequency in sorted(word_freq.iteritems(), key=lambda (k, v): (v, k), reverse=descendent):
        words_stats.append({"index": words[word], "frequency": frequency, "value": word})

    return words, word_freq, words_stats


def map_sentences_to_word_stats(x, word_stats, map_size):
    sentences = []
    for row in x:
        sentence = [0] * map_size
        for index, word_stat in enumerate(word_stats[0:map_size]):
            if word_stat["value"] in row:
                sentence[index] = 1
        sentences.append(sentence)
    return sentences



def vectorize(x, word_dict_len, sentence_words_max):
    #sentences_list = [y1 for y2 in x for y1 in y2]
    sentences_list = x
    tokenizer = Tokenizer(num_words=word_dict_len)
    tokenizer.fit_on_texts(sentences_list)
    sequences = tokenizer.texts_to_sequences(sentences_list)
    word_index = tokenizer.word_index
    print('Found %s unique tokens.' % len(word_index))
    data = preprocessing.sequence.pad_sequences(sequences, maxlen=sentence_words_max)
    return data, word_index



def lemmatize(token, tag):
    lemmatizer = WordNetLemmatizer()
    if tag[0].lower() in ['n', 'v']:
        r = lemmatizer.lemmatize(token, tag[0].lower())
        return r
    return token

def lemmatize_it(x):
    tagged_corpus = [pos_tag(word_tokenize(document[0])) for document in x]
    return [[lemmatize(token, tag) for token, tag in document] for document in tagged_corpus]


def lowercaseit_remove_stopwords_lemmatize(x):
    _x = [[o[0].lower()] for o in x]
    return lemmatize_it(remove_stopwords(np.asarray(_x)))


def lowercaseit(x):
    _x = [[o[0].lower()] for o in x]
    return np.asarray(_x)


def remove_stopwords_lemmatize(x):
    return lemmatize_it(remove_stopwords(x))

def remove_stop_words_from_str(s):
    r = []
    for x in s.split():
        if x.strip() not in stopwords.words('english') and x.strip() not in string.punctuation and re.match('\'.$', x.strip()) is None and x.strip() not in[str('ibm')] :
            r.append(x.strip())
    return [' '.join(r)]

def remove_stopwords_from_str(s):
    r = []
    for x in s.split():
        if x.strip() not in stopwords.words('english'):
            r.append(x.strip())
    return [' '.join(r)]

def remove_apostrophes_from_str(s):
    r = []
    for x in s.split():
        _x = x.strip()
        if re.match('\w+\'.', _x) is not None:
            _x = _x[:-2]
        if re.match('\'.$', _x) is None:
            r.append(_x)
    return [' '.join(r)]

def remove_punctuation_from_str(s):
    r = []
    for x in s.split():
        if x.strip() not in string.punctuation:
            r.append(x.strip())
    return [' '.join(r)]

def remove_stopwords(x):
    return [remove_stopwords_from_str(k) for k in x[:, 0]]

def remove_apostrophes(x):
    return [remove_apostrophes_from_str(k) for k in x[:, 0]]

def remove_punctuation(x):
    x = [remove_punctuation_from_str(k) for k in x[:, 0]]
    return np.asarray(x)



def normalize_data_array(arr):
    mean = arr.mean(axis=0)
    arr -= mean
    std = arr.std(axis=0)
    arr /= std
    return arr


def generator(data, lookback, delay, min_index, max_index, batch_size=128):
    if max_index is None:
        max_index = len(data) - delay - 1
    i = min_index + lookback
    while 1:
        if i + batch_size >= max_index:
            i = min_index + lookback
        time_window_analysis_indexes = np.arange(i, min(i + batch_size, max_index))
        i += len(time_window_analysis_indexes)
        samples = np.zeros((len(time_window_analysis_indexes), lookback, data.shape[-1]))
        targets = np.zeros((len(time_window_analysis_indexes),))
        for j, row in enumerate(time_window_analysis_indexes):
            training_indices = range(time_window_analysis_indexes[j] - lookback, time_window_analysis_indexes[j])
            samples[j] = data[training_indices]
            targets[j] = data[time_window_analysis_indexes[j] + delay][3]
        yield samples, targets


decisionNode = dict(boxstyle="sawtooth", fc="0.8")
leafNode = dict(boxstyle="round4", fc="0.8")
arrow_args = dict(arrowstyle="<-")


def plotNode(nodeTxt, centerPt, parentPt, nodeType):
    createPlot.ax1.annotate(nodeTxt, xy=parentPt, xycoords='axes fraction', xytext=centerPt,
                            textcoords='axes fraction', va="center", ha="center",
                            bbox=nodeType, arrowprops=arrow_args)


def createPlot():
    fig = plt.figure(1, facecolor='white')
    fig.clf()
    createPlot.ax1 = plt.subplot(111, frameon=False)
    plotNode('a decision node', (0.5, 0.1), (0.1, 0.5), decisionNode)
    plotNode('a leaf node', (0.8, 0.1), (0.3, 0.8), leafNode)
    plt.show()


def getNumLeafs(myTree):
    numLeafs = 0
    firstStr = myTree.keys()[0]
    secondDict = myTree[firstStr]
    for key in secondDict.keys():
        if type(secondDict[key]).__name__=='dict':
            numLeafs += getNumLeafs(secondDict[key])
        else:
            numLeafs +=1
    return numLeafs

def getTreeDepth(myTree):
    maxDepth = 0
    firstStr = myTree.keys()[0]
    secondDict = myTree[firstStr]
    for key in secondDict.keys():
        if type(secondDict[key]).__name__=='dict':
            thisDepth = 1 + getTreeDepth(secondDict[key])
        else:
            thisDepth = 1
        if thisDepth > maxDepth: maxDepth = thisDepth
    return maxDepth

def plotMidText(cntrPt, parentPt, txtString):
    xMid = (parentPt[0]-cntrPt[0])/2.0 + cntrPt[0]
    yMid = (parentPt[1]-cntrPt[1])/2.0 + cntrPt[1]
    createPlot.ax1.text(xMid, yMid, txtString)


def plotTree(myTree, parentPt, nodeTxt):
    numLeafs = getNumLeafs(myTree)
    getTreeDepth(myTree)
    firstStr = myTree.keys()[0]
    cntrPt = (plotTree.xOff + (1.0 + float(numLeafs))/2.0/plotTree.totalW,plotTree.yOff)
    plotMidText(cntrPt, parentPt, nodeTxt)
    plotNode(firstStr, cntrPt, parentPt, decisionNode)
    secondDict = myTree[firstStr]
    plotTree.yOff = plotTree.yOff - 1.0/plotTree.totalD
    for key in secondDict.keys():
        if type(secondDict[key]).__name__=='dict':
            plotTree(secondDict[key],cntrPt,str(key))
        else:
            plotTree.xOff = plotTree.xOff + 1.0/plotTree.totalW
            plotNode(secondDict[key], (plotTree.xOff, plotTree.yOff), cntrPt, leafNode)
            plotMidText((plotTree.xOff, plotTree.yOff), cntrPt, str(key))
    plotTree.yOff = plotTree.yOff + 1.0/plotTree.totalD


def createPlot(inTree):
    fig = plt.figure(1, facecolor='white')
    fig.clf()
    axprops = dict(xticks=[], yticks=[])
    createPlot.ax1 = plt.subplot(111, frameon=False, **axprops)
    plotTree.totalW = float(getNumLeafs(inTree))
    plotTree.totalD = float(getTreeDepth(inTree))
    plotTree.xOff = -0.5/plotTree.totalW; plotTree.yOff = 1.0;
    plotTree(inTree, (0.5,1.0), '')
    plt.show()


def calc_shannon_entropy(dataSet):

    numEntries = len(dataSet)

    labelCounts = {}
    for featVec in dataSet:
        currentLabel = featVec[-1]
        if currentLabel not in labelCounts.keys():
            labelCounts[currentLabel] = 0
        labelCounts[currentLabel] += 1
    shannonEnt = 0.0
    for key in labelCounts:
        prob = float(labelCounts[key]) / numEntries
        shannonEnt -= prob * log(prob, 2)
    return shannonEnt


def splitDataSet(dataSet, axis, value):
    r = []
    for featVec in dataSet:
        if featVec[axis] == value:
            reducedFeatVec = featVec[:axis]
            reducedFeatVec.extend(featVec[axis+1:])
            r.append(reducedFeatVec)
    return r


def chooseBestFeatureToSplit(dataSet):
    numFeatures = len(dataSet[0]) - 1
    baseEntropy = calc_shannon_entropy(dataSet)
    bestInfoGain = 0.0; bestFeature = -1
    for i in range(numFeatures):
        featList = [example[i] for example in dataSet if example[i] != 0]
        uniqueVals = set(featList)
        newEntropy = 0.0
        for value in uniqueVals:
            subDataSet = splitDataSet(dataSet, i, value)
            prob = len(subDataSet)/float(len(dataSet))
            newEntropy += prob * calc_shannon_entropy(subDataSet)
        infoGain = baseEntropy - newEntropy
        if (infoGain > bestInfoGain):
            bestInfoGain = infoGain
            bestFeature = i
    return bestFeature


def majorityCnt(classList):
    classCount={}
    for vote in classList:
        if vote not in classCount.keys(): classCount[vote] = 0
        classCount[vote] += 1
    sortedClassCount = sorted(classCount.iteritems(), key=operator.itemgetter(1), reverse=True)
    return sortedClassCount[0][0]


def createTree(dataSet,labels):
    classList = [example[-1] for example in dataSet]
    if classList.count(classList[0]) == len(classList):
        return classList[0]
    if len(dataSet[0]) == 1:
        return majorityCnt(classList)
    bestFeat = chooseBestFeatureToSplit(dataSet)
    bestFeatLabel = labels[bestFeat]
    myTree = {bestFeatLabel:{}}
    del(labels[bestFeat])
    featValues = [example[bestFeat] for example in dataSet]
    uniqueVals = set(featValues)
    for value in uniqueVals:
        subLabels = labels[:]
        myTree[bestFeatLabel][value] = createTree(splitDataSet(dataSet, bestFeat, value),subLabels)
    return myTree


def classify(inputTree,featLabels,testVec):
    firstStr = inputTree.keys()[0]
    secondDict = inputTree[firstStr]
    featIndex = featLabels.index(firstStr)
    for key in secondDict.keys():
        if testVec[featIndex] == key:
            if type(secondDict[key]).__name__=='dict':
                classLabel = classify(secondDict[key],featLabels,testVec)
            else: classLabel = secondDict[key]
    return classLabel


def sequence_analysis_naive(validation_steps, validation_data_generator):
    batch_maes = []
    for step in range(validation_steps):
        samples, targets = next(validation_data_generator)
        # get the last of the history from all the entries
        preds = samples[:, -1, 3]
        mae = np.mean(np.abs(preds - targets))
        batch_maes.append(mae)
    print(np.mean(batch_maes))




def small_densely_connected_model(data, lookback, train_data_generator
                                  , validation_data_generator, validation_steps):
    model = Sequential()
    model.add(layers.Flatten(input_shape=(lookback, data.shape[-1])))
    model.add(layers.Dense(32, activation='relu'))
    model.add(layers.Dense(1))
    model.compile(optimizer=RMSprop(), loss='mae')
    history = model.fit_generator(train_data_generator,
                                  steps_per_epoch=500,
                                  epochs=20,
                                  validation_data=validation_data_generator,
                                  validation_steps=validation_steps)
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    epochs = range(1, len(loss) + 1)
    plt.figure()
    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()
    plt.show()


def gru_based_model(data, lookback, train_data_generator
                                  , validation_data_generator, validation_steps):

    model = Sequential()
    model.add(layers.GRU(32, input_shape=(None, data.shape[-1])))
    model.add(layers.Dense(1))
    model.compile(optimizer=RMSprop(), loss='mae')
    history = model.fit_generator(train_data_generator,
                                  steps_per_epoch=500,
                                  epochs=20,
                                  validation_data=validation_data_generator,
                                  validation_steps=validation_steps)
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    epochs = range(1, len(loss) + 1)
    plt.figure()
    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()
    plt.show()


def gru_with_dropout_model(data, lookback, train_data_generator
                                  , validation_data_generator, validation_steps):

    model = Sequential()
    model.add(layers.GRU(32, dropout=0.2, recurrent_dropout=0.2, input_shape=(None, data.shape[-1])))
    model.add(layers.Dense(1))
    model.compile(optimizer=RMSprop(), loss='mae')
    history = model.fit_generator(train_data_generator,
                                  steps_per_epoch=500,
                                  epochs=40,
                                  validation_data=validation_data_generator,
                                  validation_steps=validation_steps)
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    epochs = range(1, len(loss) + 1)
    plt.figure()
    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()
    plt.show()


def gru_with_dropout_stacked_model(data, lookback, train_data_generator
                                  , validation_data_generator, validation_steps):

    model = Sequential()
    model.add(layers.GRU(32, dropout=0.1, recurrent_dropout=0.5, return_sequences=True,
                         input_shape=(None, data.shape[-1])))
    model.add(layers.GRU(64, activation='relu',dropout=0.1, recurrent_dropout=0.5))
    model.add(layers.Dense(1))
    model.compile(optimizer=RMSprop(), loss='mae')
    history = model.fit_generator(train_data_generator,
                                  steps_per_epoch=500,
                                  epochs=40,
                                  validation_data=validation_data_generator,
                                  validation_steps=validation_steps)
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    epochs = range(1, len(loss) + 1)
    plt.figure()
    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()
    plt.show()

def gru_bidirectional_model(data, lookback, train_data_generator
                                  , validation_data_generator, validation_steps):

    model = Sequential()
    model.add(layers.Bidirectional(
        layers.GRU(32), input_shape=(None, data.shape[-1])))
    model.add(layers.Dense(1))
    model.compile(optimizer=RMSprop(), loss='mae')
    history = model.fit_generator(train_data_generator,
                                  steps_per_epoch=500,
                                  epochs=40,
                                  validation_data=validation_data_generator,
                                  validation_steps=validation_steps)
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    epochs = range(1, len(loss) + 1)
    plt.figure()
    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()
    plt.show()


def plot_oversampling():
    import matplotlib.pyplot as plt
    import numpy as np

    classes = ['positive', 'neutral', 'negative']
    n = ['initial', 'oversampled']
    pos = np.arange(len(classes))
    initial = [4771, 3044, 632]
    oversampled = [0, 1737, 4139]

    plt.bar(pos, initial, color='gray', edgecolor='black')
    plt.bar(pos, oversampled, color='white', edgecolor='black', bottom=initial)
    plt.xticks(pos, classes)
    plt.xlabel('classes', fontsize=16)
    plt.ylabel('number of observations', fontsize=16)
    plt.title('Observations oversampling', fontsize=18)
    plt.legend(n, loc=2)
    plt.show()

