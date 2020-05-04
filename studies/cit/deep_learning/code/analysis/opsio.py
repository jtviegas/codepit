import csv
import unidecode
import numpy as np
import os


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


def get_glove_world_embeddings(glove_dir, max_dict_words, word_index):

    embeddings_index = {}
    glove_embedding_dim = 100
    glove_embedding_file = 'glove.6B.100d.txt'

    f = open(os.path.join(glove_dir, glove_embedding_file))
    """generate index from embeddings file"""
    for line in f:
        values = line.split()
        word = values[0]
        embeddings_index[word] = np.asarray(values[1:], dtype='float32')
    f.close()
    print('Found %s word vectors.' % len(embeddings_index))

    embedding_matrix = np.zeros((max_dict_words, glove_embedding_dim))
    for word, i in word_index.items():
        if i < max_dict_words:
            embedding_vector = embeddings_index.get(word)  # get its embedding vector
            if embedding_vector is not None:  # create the matrix with it
                embedding_matrix[i] = embedding_vector

    return embedding_matrix


if __name__ == "__main__":
    try:

        print('done')
    except ValueError as e:
        print("sorry there was a processing exception:", e)
