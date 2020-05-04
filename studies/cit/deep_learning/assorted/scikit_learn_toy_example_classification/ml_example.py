

from sknn.mlp import Classifier

from sklearn import tree
from sklearn.feature_selection import RFE
from sklearn.feature_selection import SelectKBest
from sklearn.grid_search import GridSearchCV

import random

#pre-processing data
#fName = "iris.data"
fName = "bezdekIris.data"
data=[map(str, line.split(",")) for line in open(fName).readlines()]

features = []
labels = []

#initial seed number (to reproduce the experiment)
#random.seed(42)

#shuffling data
random.shuffle(data)

label_index = 0
label_dir = {}
for sample_i in data:
    label = sample_i[len(sample_i)-1]
    label = label.strip("\n")
    if not label_dir.has_key(label):
        label_dir[label] = label_index
        label_index+=1
    labels.append(label_dir[label])
    feature_sample = map(lambda x: float(x), sample_i[:len(sample_i)-1])
    features.append(feature_sample)

for i in range(0, len(labels)):
    print features[i], labels[i]
print "Labels: ",len(label_dir)
print "Features: ",len(features)

#splitting data into training and testing sets
features_train = []
label_train    = []

features_test  = []
label_test     = []

train_size = int(len(features)*0.7)
test_size  = len(features) - train_size



for i in range(0, train_size):
    features_train.append(features[i])
    label_train.append(labels[i])

for i in range(train_size, train_size+test_size):
    features_test.append( features[i] )
    label_test.append( labels[i] )

#print features_train
#print label_train


clf = tree.DecisionTreeClassifier()
clf.fit(features_train, label_train)

predictions = clf.predict(features_test)

misclassify = 0
for pred_i in range(0, len(predictions)):
    # print predictions[pred_i], label_test[pred_i]
    if predictions[pred_i] != label_test[pred_i]:
        misclassify += 1
accuracy = (len(predictions) - misclassify)/float(len(predictions))
misclassification = misclassify/float(len(predictions))
print "Accuracy: ", accuracy
print "Misclassification: ",misclassification