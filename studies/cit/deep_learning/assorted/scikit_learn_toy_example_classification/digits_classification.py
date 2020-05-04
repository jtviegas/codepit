
# EXAMPLE OF CLASSIFICATION 

from sklearn import datasets
iris = datasets.load_iris()
digits = datasets.load_digits()

print (digits.target)

# For instance, in the case of the digits dataset, digits.data gives access to
# the features that can be used to classify the digits samples:

# digits.target are the labels of the samples of digit.images 

print (digits.images[0])
#The data is always a 2D array, shape (n_samples, n_features), 
#although the original data may have had a different shape. In the case of the digits, 
#each original sample is an image of shape (8, 8) and can be accessed using:

from sklearn import svm
clf = svm.SVC(gamma=0.001, C=100.)
# In this example we set the value of gamma manually.
# It is possible to automatically find good values for the parameters 
#by using tools such as grid search and cross validation.

# We call our estimator instance clf, as it is a classifier. It now must be fitted to the model, 
# that is, it must learn from the model. This is done by passing our training set to the fit method. 
# As a training set, let us use all the images of our dataset apart from the last one. 
# We select this training set with the [:-1] Python syntax, which produces a new array that contains all but the last entry of digits.data:

features_train =  digits.data[:-1]
labels_train =  digits.target[:-1]

clf.fit(features_train, labels_train)

# Now you can predict new values, in particular, we can ask to the classifier what is the digit of our last image 
# in the digits dataset, which we have not used to train the classifier:
features_test = digits.data[-1:]


train_size = len(features_train)
test_size  = len(features_test)

print("train_size=", train_size)
print("test_size=", test_size)


prediction = clf.predict(features_test)

print("prediction=", prediction)
# In this case, it is printing an 8 
