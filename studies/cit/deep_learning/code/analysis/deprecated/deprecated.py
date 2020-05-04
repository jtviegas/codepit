import numpy as np
import matplotlib.pyplot as plt





def plot_metrics():

    accuracy = (75.7, 43.9, 79.9, 77.8, 80.1)

    ind = np.arange(len(accuracy))  # the x locations for the groups
    width = 0.30  # the width of the bars

    fig, ax = plt.subplots()
    rects1 = ax.bar(ind, accuracy, width, color='SkyBlue', label='accuracy')

    # Add some text for labels, title and custom x-axis tick labels, etc.
    ax.set_ylabel('percentage (%)')
    ax.set_title('test avg accuracy by model')
    ax.set_xticks(ind)
    ax.set_xticklabels(('Naive-Bayes', '1 h l', '2 h l + embed.', '1 h l + Glove embed.', '1D CNN'))
    ax.xaxis.set_tick_params(rotation=15)
    for tick in ax.xaxis.get_major_ticks():
        tick.label.set_fontsize(14)
        # specify integer or one of preset strings, e.g.
        # tick.label.set_fontsize('x-small')
       #tick.label.set_rotation(70)
    ax.legend()

    def autolabel(rects, xpos='center'):
        """
        Attach a text label above each bar in *rects*, displaying its height.

        *xpos* indicates which side to place the text w.r.t. the center of
        the bar. It can be one of the following {'center', 'right', 'left'}.
        """

        xpos = xpos.lower()  # normalize the case of the parameter
        ha = {'center': 'center', 'right': 'left', 'left': 'right'}
        offset = {'center': 0.5, 'right': 0.57, 'left': 0.43}  # x_txt = x + w*off

        for rect in rects:
            height = rect.get_height()
            ax.text(rect.get_x() + rect.get_width() * offset[xpos], 1.01 * height,
                    '{}'.format(height), ha=ha[xpos], va='bottom')

    autolabel(rects1, "left")


    plt.show()

plot_metrics()