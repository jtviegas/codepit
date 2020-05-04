import common

if __name__ == "__main__":
    try:

        TEST_DATA = '/home/jtviegas/Documents/github/studies/cit/deep_learning/data/stock.csv'
        YEARS = [2009, 2016]
        TESTING_FRACTION=0.3
        VALIDATION_FRACTION=0.3
        batch_size = 128

        lookback = 21 # Observations will go back 3 weeks
        steps = 1  # Observations will be sampled at one data point per day=no sampling
        delay = 7 # Targets will be one week

        dates, data = common.get_x_and_y_stocks(TEST_DATA, YEARS[0], YEARS[1])
        # normalize data
        data = common.normalize_data_array(data)

        sample_size = data.shape[0]
        testing_size = int(sample_size * TESTING_FRACTION)
        validation_size = int((sample_size - testing_size)*VALIDATION_FRACTION)
        training_size = sample_size - testing_size - validation_size

        training_data = common.generator(data, lookback=lookback, delay=delay, min_index=0, max_index=training_size - 1, batch_size=batch_size)
        validation_data = common.generator(data, lookback=lookback, delay=delay, min_index=training_size, max_index=training_size + validation_size - 1,
                                           batch_size=batch_size)
        testing_data = common.generator(data, lookback=lookback, delay=delay, min_index=training_size + validation_size, max_index=sample_size - 1,
                                        batch_size=batch_size)

        #common.sequence_analysis_naive(validation_size - lookback, validation_data)

        common.gru_bidirectional_model(data, lookback, training_data, validation_data, validation_size - lookback)



        print('done')

    except ValueError as e:
        print("sorry there was a processing exception:", e)