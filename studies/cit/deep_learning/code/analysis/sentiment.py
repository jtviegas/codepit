from keras import layers
from keras.layers import Flatten, Dense, LeakyReLU, PReLU
from keras.layers import Embedding, Dropout
from keras import utils
from keras.models import Sequential
from sklearn.model_selection import StratifiedKFold
import numpy as np

import opsio
import opsdata
import opsplot


def train_and_evaluate_model(x, y, model_function, model_parameters, epochs=8, batch_size=256, plot=False):
    metrics = ['acc', 'recall', 'precision', 'f1_score']

    kfold = StratifiedKFold(n_splits=10, shuffle=True)
    fold_scores = []

    for train, test in kfold.split(x, y):
        x_oversampled, y_oversampled = opsdata.oversample(x[train], y[train])
        model = model_function(model_parameters)
        model.summary()
        model.compile(optimizer='rmsprop', loss='categorical_crossentropy',
                      metrics=['acc', opsdata.recall, opsdata.precision, opsdata.f1_score])
        history = model.fit(x_oversampled, utils.to_categorical(y_oversampled, num_classes=3),
                            epochs=epochs,
                            batch_size=batch_size)
        scores = model.evaluate(x[test], utils.to_categorical(y[test], num_classes=3), verbose=0)
        score = []
        for i in range(0, len(metrics)):
            print("%s: %.2f%%" % (model.metrics[i], scores[i + 1] * 100))
            print("_________________________________________________________")
            score.append(scores[i + 1] * 100)
        fold_scores.append(score)

    final_scores = np.asarray(fold_scores)
    print("___________________________final scores______________________________")
    for i in range(0, len(metrics)):
        print("%s: %.2f%% (+/- %.2f%%)" % (
            metrics[i], np.mean(final_scores[:, i]), np.std(final_scores[:, i])))

    # plot the latest trained model as a sample
    if plot:
        opsplot.plot_model_history(history, metrics, validation=False)

    return model, history


def model_cnn_1d(params):
    model = Sequential()
    model.add(Embedding(params['dict_size'], params['embedding_size'], input_length=params['sentence_size']))
    model.add(layers.Conv1D(8, 24, activation='relu'))
    #model.add(layers.Conv1D(8, 24, activation='relu'))
    #model.add(layers.MaxPooling1D())
    #model.add(layers.Conv1D(128, 12, activation='relu'))
    #model.add(layers.Conv1D(128, 12, activation='relu'))
    model.add(layers.GlobalMaxPooling1D())
    #model.add(Dropout(0.5))
    model.add(Dense(3, activation='softmax'))
    return model


def model_one_hidden_with_glove_embeddings(params):
    embedding_matrix = opsio.get_glove_world_embeddings(params['glove_dir'], params['dict_size'], params['word_index'])
    model = Sequential()
    model.add(Embedding(params['dict_size'], embedding_matrix.shape[1], input_length=params['sentence_size']))
    model.add(Flatten())
    model.add(Dense(1024, activation=PReLU(alpha_initializer='zeros', alpha_regularizer=None, alpha_constraint=None, shared_axes=None)))
    model.add(Dense(3, activation='softmax'))
    model.summary()
    model.layers[0].set_weights([embedding_matrix])
    model.layers[0].trainable = False
    return model


def model_two_hidden_with_embeddings(params):
    model = Sequential()
    model.add(Embedding(params['dict_size'], params['embedding_size'], input_length=params['sentence_size']))
    model.add(Flatten())
    model.add(Dense(32, activation='relu'))
    model.add(Dense(32, activation='relu'))
    model.add(Dense(3, activation='softmax'))
    model.summary()
    return model


def model_one_hidden(params):
    model = Sequential()
    model.add(Dense(32, activation='relu', input_shape=(params['sentence_size'],)))
    model.add(Dense(32, activation='relu'))
    model.add(Dense(3, activation='softmax'))
    model.summary()
    return model


def run_experiment(data, option=4, dict_size=25000, sentence_size=100, embedding_size=128, glove_dir=None):

    x, y, dummy, dummy, dummy, dummy, word_index = \
            opsdata.load_dataset(data, dict_size, sentence_size, test_ratio=0.0, val_ratio=0.0, oversampling=False)

    params = {"sentence_size": sentence_size, "dict_size": dict_size, "embedding_size": embedding_size,
              "glove_dir": glove_dir, "word_index": word_index}

    if option == 1:
        func = lambda parameters: model_one_hidden(parameters)
    elif option == 2:
        func = lambda parameters: model_one_hidden_with_embeddings(parameters)
    elif option == 3:
        func = lambda parameters: model_one_hidden_with_glove_embeddings(parameters)
    elif option == 4:
        func = lambda parameters: model_cnn_1d(parameters)

    train_and_evaluate_model(x, y, func, params, epochs=epochs, batch_size=batch_size, plot=True)


if __name__ == "__main__":
    try:

        GLOVE_DIR = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data/glove.6B'
        DATA = 'data.csv'

        DICT_SIZE = 25000
        SENTENCE_SIZE = 100
        EMBEDDING_SIZE = 64
        epochs = 16
        batch_size = 256

        run_experiment(DATA, 1, DICT_SIZE, SENTENCE_SIZE, EMBEDDING_SIZE, GLOVE_DIR)

        print('done')

    except ValueError as e:
        print("sorry there was a processing exception:", e)
