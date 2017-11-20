import numpy as np
import pandas as pd
import matplotlib.pyplot as plt



def compareAverage(a1, a2):
    av1 = np.mean(a1)
    av2 = np.mean(a2)
    l1 = len(a1)
    l2 = len(a2)
    print('n1: {n1}   n2: {n2}'.format(n1=l1, n2=l2))
    print('av1: {av1}   av2: {av2}'.format(av1=av1, av2=av2))

    result = 0
    if av1 != av2:
        if av1 > av2:
            result = 1
        else:
            result = -1
    return result


'''
compare the average number of users on holidays (1) and
non-holidays (0) (column index 5)
'''
def ex_w10_01():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')

    total = np.average(data[:,15])
    print('overall average: {av}'.format(av=total))
    on_holidays = data[data[:,5] == 1]
    non_holidays = data[data[:,5] == 0]
    comparison = compareAverage(on_holidays[:,15], non_holidays[:,15])
    print('comparison on holidays vs non holidays: {c}'.format(c=comparison))

    comparison = compareAverage(on_holidays[:, 13], non_holidays[:, 14])
    print('comparison on holidays+casual vs non holidays+non casual: {c}'.format(c=comparison))

'''
Column 13 of the bike dataset contains the number of casual users for a given row,
while column 14 contains the number of registered users.
For each row we wish to capture the higher value and store in an array. For
example, if in row 1, the value for column 13 was 35 and for registered was 99
then we could exact the 99. We then want to print the mean of these numbers.
'''
def ex_w10_02():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')

    casual = data[:, 13]
    registered = data[:, 14]
    total = np.where(casual>registered, casual, registered)
    print(np.mean(total))

def ex_w10_03():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')

    temps = np.array([[1,5], [6,10], [11,15], [16,20], [21,25], [26,30], [31,35], [36,40]], float)
    for t in temps:
        d = data[np.logical_and(data[:,9]*41 > t[0], data[:,9]*41 <= t[1])]
        print('between temperature {a} and {b} average casual users is: {u}'.format(
            a=t[0], b=t[1], u=np.mean(d[:,13])))


def ex_w10_04():
    d = {'jim':78, 'elaine':23, 'ted':65, 'frank':88, 'sarah':80, 'tim': 33}
    s = pd.Series(d)
    r = s[s<40]
    print(r)
    print(r+5)


def ex_w11_01():
    data = pd.read_csv('resources/titanic.csv')
    died = data[data['Survived'] == 0]
    died_pclass = died.groupby('Pclass', sort=True)
    y = died_pclass['Survived'].count()
    print(y)
    plt.plot([1,2,3], y)
    plt.show()

def ex_lab_w9_01():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')
    temp = data[:,9]*41
    average=np.mean(temp)
    print('average is:{t}'.format(t=average))

def ex_lab_w9_02():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')
    holidays = data[data[:,5]==1]
    non_holidays = data[data[:, 5] == 0]

    average_users_holidays = np.mean(holidays[:,15])
    average_users_non_holidays = np.mean(non_holidays[:, 15])

    print('holidays average is:{t}'.format(t=average_users_holidays))
    print('non holidays average is:{t}'.format(t=average_users_non_holidays))

def ex_lab_w9_03():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')
    for m in np.sort(np.unique(data[:,3])):
        month_data = data[data[:, 3] == m]
        print('{m}: {t}'.format(m=m, t=np.sum(month_data[:, 13])))

def ex_lab_w9_04():
    data = np.genfromtxt('resources/hours.csv', delimiter=',')
    ranges = np.array([[1,5],[6,10],[11,15],[16,20],[21,25],[26,30],[31,35],[36,40]])
    for r in ranges:
        records_in_range = data[np.logical_and(data[:,9]*41 > r[0], data[:,9]*41<= r[1])]
        print('{s}:{e} => {m}'.format(s=r[0],e=r[1],m=np.mean(records_in_range[:,13])))

def ex_lab_w10_01():
    data = pd.read_csv('resources/attacks.csv')
    counts = data['Location'].value_counts()
    sorted = counts.sort_values(ascending=False)
    print(sorted.keys()[0])
    print(sorted[0])

def ex_lab_w10_012():
    data = pd.read_csv('resources/attacks.csv')
    counts = data['Country'].value_counts()
    sorted = counts.sort_values(ascending=False)
    print(sorted[0:6])

def ex_lab_w10_013():
    data = pd.read_csv('resources/attacks.csv')
    fatal = data[data['Fatal'] == 'Y']
    counts = fatal['Country'].value_counts()
    sorted = counts.sort_values(ascending=False)
    print(sorted[0:6])

def ex_lab_w10_014():
    data = pd.read_csv('resources/attacks.csv')
    scuba_condition = data['Activity'] == 'Scuba Diving'
    surf_condition = data['Activity'] == 'Surfing'
    subset = data[scuba_condition | surf_condition]
    counts = subset['Activity'].value_counts()
    print(counts)

def ex_lab_w10_02():
    data = pd.read_csv('resources/attacks.csv')
    n = data['Case Number'].count()
    n_fatal = data[data['Fatal'] == 'Y']['Case Number'].count()
    print(n_fatal*100/n)

def ex_lab_w10_022():
    data = pd.read_csv('resources/attacks.csv')
    fatal = data[data['Fatal'] == 'Y']
    for c in data['Country'].unique():
        country_data = data[data['Country'] == c]
        n = country_data['Fatal'].count()
        n_fatal = country_data[country_data['Fatal'] == 'Y']['Fatal'].count()
        print('{c}: {t}'.format(c=c, t=n_fatal*100/n))
def jitter(vectorNums):
    stdev = 0.01*(max(vectorNums)-min(vectorNums))
    return vectorNums + np.random.randn(len(vectorNums)) * stdev

def ex_lab_w11_02():
    df = pd.read_csv('resources/adultDataset.csv')
    dataGreater50K = df[df["Income"] == ">50K"]
    dataLess50K = df[df["Income"] == "<=50K"]
    plt.scatter(jitter(dataGreater50K['education-num']), dataGreater50K['hours-per-week'], color='r')
    plt.scatter(jitter(dataLess50K['education-num']), dataLess50K['hours-per-week'], color='g', s=2)
    #plt.scatter(dataGreater50K['education-num'], dataGreater50K['hours-per-week'], color='r')
    #plt.scatter(dataLess50K['education-num'], dataLess50K['hours-per-week'], color='g', s=2)
    plt.show()



if __name__ == "__main__":
    ex_lab_w11_02()

