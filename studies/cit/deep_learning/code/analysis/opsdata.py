import numpy as np
import pandas as pd
from imblearn.over_sampling import RandomOverSampler
from keras import backend as kback

import opsio
import opstext


def shuffle_and_split_data(x, y, testing_fraction, validation_fraction=0.0):

    d = np.concatenate((x, y), axis=1)
    np.random.shuffle(d)
    variables = d.shape[1]
    y = np.asarray([[o] for o in d[:, -1]])
    x = d[:, :variables-1]

    num_samples = d.shape[0]
    testing_samples = int(num_samples * testing_fraction)
    total_training_samples = num_samples - testing_samples
    validation_samples = int(total_training_samples * validation_fraction)
    training_samples = total_training_samples - validation_samples

    x_train = x[:training_samples]
    y_train = y[:training_samples]

    if testing_samples > 0:
        x_test = x[training_samples + validation_samples:]
        y_test = y[training_samples + validation_samples:]
        print('Shape of x_test tensor:', x_test.shape)
        print('Shape of y_test tensor:', y_test.shape)
    else:
        x_test = None
        y_test = None

    if validation_samples > 0:
        x_validation = x[training_samples: training_samples + validation_samples]
        y_validation = y[training_samples: training_samples + validation_samples]
        print('Shape of x_validation tensor:', x_validation.shape)
        print('Shape of y_validation tensor:', y_validation.shape)
    else:
        x_validation = None
        y_validation = None

    print('Shape of x_train tensor:', x_train.shape)
    print('Shape of y_train tensor:', y_train.shape)

    return x_train, y_train, x_validation, y_validation, x_test, y_test


def oversample(x, y):

    ys = pd.DataFrame(y)
    print(ys[0].value_counts())
    sm = RandomOverSampler()
    _x, _y = sm.fit_sample(x, y[:, 0])
    ys = pd.DataFrame(_y)
    print(ys[0].value_counts())
    return _x, _y


def load_dataset(data, dict_size, sentence_size, test_ratio=0.3, val_ratio=0.0, oversampling=False, use_sequences=True):
    x, y = opsio.get_x_and_y(data)
    x = opstext.lemmatize_it(opstext.remove_stopwords(
        opstext.lowercase(opstext.remove_apostrophes(
            opstext.remove_punctuation(x)))))

    result_sequences, word_index, result_one_hot_encoding = opstext.vectorize(x, dict_size, sentence_size)

    if use_sequences:
        x = result_sequences
    else:
        x = result_one_hot_encoding

    x_train, y_train, x_validation, y_validation, x_test, y_test = shuffle_and_split_data(x, y, test_ratio, val_ratio)
    if oversampling:
        x_train, y_train = oversample(x_train, y_train)

    return x_train, y_train, x_validation, y_validation, x_test, y_test, word_index


def load_data(data, dict_size, sentence_size):
    x, y = opsio.get_x_and_y(data)
    x = opstext.lemmatize_it(opstext.remove_stopwords(
        opstext.lowercase(opstext.remove_apostrophes(
            opstext.remove_punctuation(x)))))
    word_sequence_matrix, word_index, sentence_word_matrix = opstext.vectorize(x, dict_size, sentence_size)

    return y, sentence_word_matrix, word_sequence_matrix, word_index


"""
Keras 1.0 metrics.
This file contains the precision, recall, and f1_score metrics which were
removed from Keras by commit: a56b1a55182acf061b1eb2e2c86b48193a0e88f7
"""
def precision(y_true, y_pred):
    """Precision metric.
    Only computes a batch-wise average of precision. Computes the precision, a
    metric for multi-label classification of how many selected items are
    relevant.
    """
    true_positives = kback.sum(kback.round(kback.clip(y_true * y_pred, 0, 1)))
    predicted_positives = kback.sum(kback.round(kback.clip(y_pred, 0, 1)))
    _precision = true_positives / (predicted_positives + kback.epsilon())
    return _precision


def confusion(y_true, y_pred):
    y_pred_pos = kback.round(kback.clip(y_pred, 0, 1))
    y_pred_neg = 1 - y_pred_pos
    y_pos = kback.round(kback.clip(y_true, 0, 1))
    y_neg = 1 - y_pos
    tp = kback.sum(y_pos * y_pred_pos) / kback.sum(y_pos)
    tn = kback.sum(y_neg * y_pred_neg) / kback.sum(y_neg)
    return {'true_pos': tp, 'true_neg': tn}


def specificity(y_true, y_pred):
    y_pred_pos = kback.round(kback.clip(y_pred, 0, 1))
    y_pred_neg = 1 - y_pred_pos
    y_pos = kback.round(kback.clip(y_true, 0, 1))
    y_neg = 1 - y_pos
    _specificity = kback.sum(y_neg * y_pred_neg) / (kback.sum(y_neg) + kback.epsilon())
    return _specificity


def recall(y_true, y_pred):
    """Recall metric.
    Only computes a batch-wise average of recall. Computes the recall, a metric
    for multi-label classification of how many relevant items are selected.
    """
    true_positives = kback.sum(kback.round(kback.clip(y_true * y_pred, 0, 1)))
    possible_positives = kback.sum(kback.round(kback.clip(y_true, 0, 1)))
    _recall = true_positives / (possible_positives + kback.epsilon())
    return _recall


def f1_score(y_true, y_pred):
    """Computes the F1 Score
    Only computes a batch-wise average of recall. Computes the recall, a metric
    for multi-label classification of how many relevant items are selected.
    """
    p = precision(y_true, y_pred)
    r = recall(y_true, y_pred)
    return (2 * p * r) / (p + r + kback.epsilon())


if __name__ == "__main__":
    try:

        print('done')
    except ValueError as e:
        print("sorry there was a processing exception:", e)
