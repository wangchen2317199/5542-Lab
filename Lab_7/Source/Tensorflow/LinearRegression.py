
from __future__ import print_function
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt

# get input data
rng = np.random
trX = []
trY = []
xy = 0
file = open('data/Input.txt','r')
for line in file.readlines():
    for i in line.split():
        if xy == 0:
            trX.append(int(i) / 1000)
            xy = 1
        else:
            trY.append(int(i) / 1000)
            xy = 0


# create symbolic variables

X = tf.placeholder("float")

Y = tf.placeholder("float")

# create a shared variable for the weight matrix

w = tf.Variable(rng.randn(), name="weights")

b = tf.Variable(rng.randn(), name="bias")

# prediction function
y_model = tf.add(tf.multiply(X, w), b)


# Mean squared error

cost = tf.reduce_sum(tf.pow(y_model-Y, 2))/(2*100)

# construct an optimizer to minimize cost and fit line to my data

train_op = tf.train.GradientDescentOptimizer(0.5).minimize(cost)


# Launch the graph in a session
sess = tf.Session()

# Initializing the variables

init = tf.global_variables_initializer()


# you need to initialize variables
sess.run(init)


for i in range(100):
    for (x, y) in zip(trX, trY):
        sess.run(train_op, feed_dict={X: x, Y: y})

print("Optimization Finished!")
training_cost = sess.run(cost, feed_dict={X: trX, Y: trY})

print("Training cost=", training_cost, "W=", sess.run(w), "b=", sess.run(b), '\n')

# Testing or Inference
test_X = np.asarray([rng.randn(),rng.randn()])
test_Y = 2*test_X + 4

print("Testing... (Mean square loss Comparison)")

testing_cost = sess.run(
    tf.reduce_sum(tf.pow(y_model - Y, 2)) / (2 * test_X.shape[0]),
    feed_dict={X: test_X, Y: test_Y})  # same function as cost above
print("Testing cost=", testing_cost)
print("Absolute mean square loss difference:", abs(
    training_cost - testing_cost))

plt.plot(trX, trY)
plt.show()