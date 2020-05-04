import matplotlib.pyplot as plt
import numpy as np


def plot_hist(data):

    n, bins, patches = plt.hist(x=data, bins='auto', color='#0504aa', alpha=0.7, rwidth=0.85)
    plt.grid(axis='y', alpha=0.75)
    plt.xlabel('Value')
    plt.ylabel('Frequency')
    plt.title('Histogram')
    plt.text(23, 45, r'$\mu=15, b=3$')
    maxfreq = n.max()
    # Set a clean upper y-axis limit.
    plt.ylim(ymax=np.ceil(maxfreq / 10) * 10 if maxfreq % 10 else maxfreq + 10)
    plt.show()


def plot_model_history(history, metrics, validation=False):

    metrics.append('loss')
    fig = plt.figure()

    for i, name in enumerate(metrics):
        metric = history.history[name];
        epochs = range(1, len(metric) + 1)
        p = fig.add_subplot(2, 3, i + 1)
        p.plot(epochs, metric, 'bo', label='Training ' + name)

        if validation:
            metric_validation = 'val_' + name
            validation = history.history[metric_validation]
            p.plot(epochs, validation, 'b', label='Validation ' + name)

        p.set_title('metric: ' + name)
        p.legend(loc=8)
    plt.show()


if __name__ == "__main__":
    try:

        print("done")
    except ValueError as e:
        print("sorry there was a processing exception:", e)