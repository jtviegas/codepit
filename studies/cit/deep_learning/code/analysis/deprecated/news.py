from requests import get
from requests.exceptions import RequestException
from contextlib import closing
from bs4 import BeautifulSoup
import csv
import numpy as np
from keras.preprocessing.text import Tokenizer

def hot_encode_sentences(array, max_words):

    tokenizer = Tokenizer(num_words=max_words)
    tokenizer.fit_on_texts(array)
    sequences = tokenizer.texts_to_sequences(array)
    one_hot_results = tokenizer.texts_to_matrix(array, mode='binary')
    word_index = tokenizer.word_index
    print('Found %s unique tokens.' % len(word_index))
    return word_index, np.array(sequences)


def get_x_and_y(data_csv):
    x = []
    y = []
    line = 0
    with open(data_csv, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            x.append([ str(row[2]) ])
            y.append([ float(row[3]) ])
            line = line + 1

    print('processed {} lines'.format(line))
    return x, y


def load_data2(data_csv, training_fraction, max_words):

    x, y = get_x_and_y(data_csv)
    sentences_list = [y1 for y2 in x for y1 in y2]
    word_index, x = hot_encode_sentences(sentences_list, max_words)
    y = np.array(y)

    dim_1_len = x.shape[0]
    training_len = int(dim_1_len * training_fraction)
    x_split = np.split(x, [training_len])
    y_split = np.split(y, [training_len])

    x_train = x_split[0]
    x_test = x_split[1]
    y_train = y_split[0]
    y_test = y_split[1]

    return x_train, y_train, x_test, y_test


def parse_html(raw_html):
    result = None
    html = BeautifulSoup(raw_html, 'html.parser')
    for div in html.select('div'):
        if div.attrs.get('class') is not None:
            for i in range(0, len(div['class'])):
                if div['class'][i] == 'StandardArticleBody_body':
                    result = div.text
                    break
    return result


def get_page_content(url):
    """
    Attempts to get the content at `url` by making an HTTP GET request.
    If the content-type of response is some kind of HTML/XML, return the
    text content, otherwise return None.
    """
    try:
        with closing(get(url, stream=True)) as resp:
            result = None
            if is_good_response(resp):
                result = parse_html(resp.content)
            return result

    except RequestException as er:
        log_error('Error during requests to {0} : {1}'.format(url, str(er)))
        return None


def is_good_response(resp):
    """
    Returns True if the response seems to be HTML, False otherwise.
    """
    content_type = resp.headers['Content-Type'].lower()
    return (resp.status_code == 200
            and content_type is not None
            and content_type.find('html') > -1)


def log_error(_err):
    print(_err)


def transform_row(row):
    new_element = ''
    text = get_page_content(row[2])
    if text is not None:
        new_element = text
    row.append(new_element)
    return row, new_element != ''


def suppress_row(row, year_start, year_end):
    return (row[0] < str(year_start)) | (row[0] > str(year_end))


def merge_news_and_sentiment(news_file, sentiment_file):

    data_array = None
    news_lines = 0
    sentiment_lines = 0
    with open(news_file, 'r') as file:
        reader = csv.reader(file, delimiter='\t')
        for row in reader:
            news_lines = news_lines + 1
            if 0 == news_lines % 50:
                print('processing news line {}'.format(news_lines))
            row[3] = ''
            del row[1]
            del row[0]
            if data_array is None:
                data_array = np.array([row])
            else:
                data_array = np.vstack((data_array, np.array([row])))

    with open(sentiment_file, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            if row[3] == '':
                data_array[sentiment_lines,1] = 0
            else:
                data_array[sentiment_lines, 1] = row[3]
            sentiment_lines = sentiment_lines + 1
            if 0 == sentiment_lines % 50:
                print('processing sentiment line {}'.format(sentiment_lines))

    print("news lines: {} sentiment lines: {}".format(news_lines, sentiment_lines))
    return data_array




def load_data(data_csv, training_fraction):

    x_array = None
    y_array = None
    line_count = 0

    with open(data_csv, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            if row[3] == '':
                data_array[sentiment_lines,1] = 0
            else:
                data_array[sentiment_lines, 1] = row[3]
            sentiment_lines = sentiment_lines + 1
            if data_array is None:
                data_array = np.array([row])
            else:
                data_array = np.vstack((data_array, np.array([row])))
            if 0 == sentiment_lines % 50:
                print('processing sentiment line {}'.format(sentiment_lines))


    data_array = merge_news_and_sentiment(news_file_tsv, sentiment_file_csv)
    dim_1_len = data_array.shape[0]
    training_len = int(dim_1_len * training_fraction)
    testing_len = dim_1_len - training_len
    data_split = np.split(data_array, [training_len])
    x_train = data_split[0][:, 0]
    y_train = data_split[0][:, 1]
    x_test = data_split[1][:, 0]
    y_test = data_split[1][:, 1]
    print('data   total len: {}   training len: {}   testing len: {}'.format(dim_1_len, training_len, testing_len))

    return x_train, y_train, x_test, y_test


def save_input_data(xtr,ytr,xt,yt, output_folder):
    numpy_to_file(xtr, '{}/x_train'.format(output_folder))
    numpy_to_file(ytr, '{}/y_train'.format(output_folder))
    numpy_to_file(xt, '{}/x_test'.format(output_folder))
    numpy_to_file(yt, '{}/y_test'.format(output_folder))

def prepare_news_input_file(src_file, output_file, years):

    data_array = None

    with open(src_file, 'r') as file:
        reader = csv.reader(file, delimiter='\t')
        rows = 0
        updates = 0
        src_lines = 0
        for row in reader:
            src_lines = src_lines + 1
            if 0 == src_lines % 50:
                print('processing line {} [selected rows: {} updated rows: {}]'.format(src_lines, rows, updates))
            del row[0]
            if not suppress_row(row, years[0], years[1]):
                row, updated = transform_row(row)
                rows = rows + 1
                if updated:
                    updates = updates + 1
                if data_array is None:
                    data_array = np.array([row])
                else:
                    data_array = np.vstack((data_array, np.array([row])))
    if data_array is not None:
        data_array = sort_by_first_column(data_array)
        numpy_to_file(data_array, output_file)
    print("wrote {} lines, with {} updates".format(rows, updates))


def sort_by_first_column(arr):
    return arr[arr[:, 0].argsort()]


def numpy_to_file(arr, output):
    np.save(output, arr)


def numpy_to_csv(arr, output):
    np.savetxt(output, arr, delimiter=',', fmt='%s')


def numpy_to_tsv(arr, output):
    np.savetxt(output, arr, delimiter='\t', fmt='%s')


def csv_to_numpy(_input):
    return np.genfromtxt(_input, delimiter=',')




if __name__ == "__main__":
    try:
        SRC_NEWS_DATA = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data/source/reuters_news_ibm.tsv'
        SENTIMENT_DATA = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data/reuters_news_ibm_sentiment.csv'
        TEST_DATA = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data/test_sentiment.csv'
        TRAINING_FRACTION = 0.7
        YEARS = list((2009, 2015))
        OUTPUT_FOLDER = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data'
        MAX_WORDS = 10000

        x_train, y_train, x_test, y_test = load_data2(TEST_DATA, TRAINING_FRACTION, MAX_WORDS)


        # x_train, y_train, x_test, y_test = load_data(TEST_DATA, TRAINING_FRACTION)
        #save_input_data(x_train, y_train, x_test, y_test, OUTPUT_FOLDER)
        #hot_encode_sentences(x_train, 10000)

    except ValueError as e:
        print("sorry there was a processing exception:", e)
