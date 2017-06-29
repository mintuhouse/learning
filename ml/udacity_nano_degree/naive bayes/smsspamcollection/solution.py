import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

# Read the tab separated data into pandas dataframe
df = pd.read_table('SMSSpamCollection', sep='\t', header=None, names=['label', 'sms_message'])

# Convert the spam, ham label to 1,0
df['label'] = df['label'].map(lambda x: {'ham': 0, 'spam': 1}[x])

# Split the data into training and test data sets
X_train, X_test, y_train, y_test = train_test_split(df['sms_message'], df['label'], test_size = 0.33, random_state = 20)

# Calculate the frequency matrixes for the train and test SMSes
count_vector = CountVectorizer()
fm_train = count_vector.fit_transform(X_train)
fm_test  = count_vector.transform(X_test)

# Use Multinomial Naive Bayes to make predictions for data
naive_bayes = MultinomialNB()
naive_bayes.fit(fm_train, y_train)
predictions = naive_bayes.predict(fm_test)

# Print out the metrics for predictions
print(accuracy_score(y_test, predictions))
print(precision_score(y_test, predictions))
print(recall_score(y_test, predictions))
print(f1_score(y_test, predictions))
