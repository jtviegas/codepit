import numpy as np
from math import log
from sklearn.model_selection import StratifiedKFold
import opsdata


def classify(x, word_class_probabilities, class_probabilities, classes):
    classification = None
    result=0
    for i in range(len(word_class_probabilities)):
        val = sum(x * word_class_probabilities[i]) + log(class_probabilities[i])
        if (classification is None) or (val > classification):
            classification = val
            result = i

    return classes[result]


def training(trainMatrix,trainCategory):

    n_docs = len(trainMatrix)
    n_words = len(trainMatrix[0])

    n_positives=0
    n_neutrals=0
    n_negatives=0

    word_count_positives = np.ones(n_words)
    word_count_neutrals = np.ones(n_words)
    word_count_negatives = np.ones(n_words)
    total_word_count_positives = 2.0
    total_word_count_neutrals = 2.0
    total_word_count_negatives = 2.0

    for i in range(n_docs):
        if trainCategory[i] == 1:
            n_positives+=1
            word_count_positives += trainMatrix[i]
            total_word_count_positives += sum(trainMatrix[i])
        else:
            if trainCategory[i] == -1:
                n_negatives += 1
                word_count_negatives += trainMatrix[i]
                total_word_count_negatives += sum(trainMatrix[i])
            else:
                n_neutrals += 1
                word_count_neutrals += trainMatrix[i]
                total_word_count_neutrals += sum(trainMatrix[i])

    positive_probability = n_positives/float(n_docs)
    neutral_probability = n_neutrals / float(n_docs)
    negative_probability = n_negatives / float(n_docs)
    positive_word_probability_vector = np.log(word_count_positives/total_word_count_positives)
    neutral_word_probability_vector = np.log(word_count_neutrals/total_word_count_neutrals)
    negative_word_probability_vector = np.log(word_count_negatives/total_word_count_negatives)

    return [positive_probability, neutral_probability, negative_probability], [positive_word_probability_vector, neutral_word_probability_vector, negative_word_probability_vector]


def testing(x, y, word_class_probabilities, class_probabilities, classes):
    total = len(x)
    t = 0.0
    for i in range(len(x)):
        classified = y[i]
        predicted = classify(x[i], word_class_probabilities, class_probabilities, classes)
        if classified == predicted:
            t += 1.0
    accuracy = t/total
    return accuracy


def train_and_evaluate_model(x, y):

    classes = [1, 0, -1]
    kfold = StratifiedKFold(n_splits=10, shuffle=True)
    accuracy_scores = []

    fold = 0
    for train, test in kfold.split(x, y):
        x_oversampled, y_oversampled = opsdata.oversample(x[train], y[train])
        class_probabilities, word_class_probabilities = training(x_oversampled, y_oversampled)
        accuracy = testing(x[test], y[test], word_class_probabilities, class_probabilities, classes)
        accuracy_scores.append(accuracy*100)
        print("accuracy at fold %d: %.2f%%" % (fold, accuracy*100))
        fold += 1

    final_scores = np.asarray(accuracy_scores)
    print("___________________________final accuracy score______________________________")
    print("%.2f%% (+/- %.2f%%)" % (np.mean(final_scores), np.std(final_scores)))


if __name__ == "__main__":
    try:
        DICT_SIZE = 25000
        SENTENCE_SIZE = 100
        DATA = 'data.csv'
        y, sentence_word_matrix, word_sequence_matrix, word_index = \
            opsdata.load_data(DATA,DICT_SIZE,SENTENCE_SIZE)

        train_and_evaluate_model(sentence_word_matrix,y)

        print('done')

    except ValueError as e:
        print("sorry there was a processing exception:", e)