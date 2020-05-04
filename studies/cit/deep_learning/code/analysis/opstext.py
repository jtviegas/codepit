import numpy as np
from nltk.corpus import stopwords
import re
import string
from keras.preprocessing.text import Tokenizer
from keras import preprocessing
from nltk import word_tokenize
from nltk.stem.wordnet import WordNetLemmatizer
from nltk import pos_tag


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


def lemmatize(token, tag):
    lemmatizer = WordNetLemmatizer()
    if tag[0].lower() in ['n', 'v']:
        r = lemmatizer.lemmatize(token, tag[0].lower())
        return r
    return token


def lemmatize_it(x):
    tagged_corpus = [pos_tag(word_tokenize(document[0])) for document in x]
    return [[lemmatize(token, tag) for token, tag in document] for document in tagged_corpus]


def remove_stopwords(x):
    return [remove_stopwords_from_str(k) for k in x[:, 0]]


def remove_apostrophes(x):
    return [remove_apostrophes_from_str(k) for k in x[:, 0]]


def remove_punctuation(x):
    x = [remove_punctuation_from_str(k) for k in x[:, 0]]
    return np.asarray(x)


def lowercase(x):
    _x = [[o[0].lower()] for o in x]
    return np.asarray(_x)


def vectorize(x, word_dict_len, sentence_words_max):
    sentences_list = x
    tokenizer = Tokenizer(num_words=word_dict_len)
    tokenizer.fit_on_texts(sentences_list)
    sequences = tokenizer.texts_to_sequences(sentences_list)
    word_index = tokenizer.word_index
    one_hot_results = tokenizer.texts_to_matrix(sentences_list, mode='binary')
    print('Found %s unique tokens.' % len(word_index))
    data = preprocessing.sequence.pad_sequences(sequences, maxlen=sentence_words_max)
    return data, word_index, one_hot_results



if __name__ == "__main__":
    try:

        print("done")
    except ValueError as e:
        print("sorry there was a processing exception:", e)
