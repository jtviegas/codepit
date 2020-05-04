import numpy as np
from deprecated import common

if __name__ == "__main__":
    try:
        TEST_DATA = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data/merge_no_ibm.csv'
        WORD_MAP_LENGTH = 10

        x, y = common.get_x_and_y(TEST_DATA)
        #x = common.lowercaseit_remove_stopwords_lemmatize(x)

        x = common.lemmatize_it(common.remove_stopwords(
            common.lowercaseit(common.remove_apostrophes(
                common.remove_punctuation(x)))))

        words, words_frequency, words_stats = common.build_word_dict_and_frequency_by_sorted_frequency(x, descendent=True)
        sentences = common.map_sentences_to_word_stats(x, words_stats, WORD_MAP_LENGTH)

        d = np.concatenate((np.asarray(sentences), y), axis=1)
        d = d.tolist()

        labels = [x["value"] for x in words_stats[0:WORD_MAP_LENGTH]]

        common.createPlot(common.createTree(d, labels))

        print('done')

    except ValueError as e:
        print("sorry there was a processing exception:", e)
