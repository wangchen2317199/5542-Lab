# File Created by Chen Wang on 03/24/17.

import tensorflow as tf

# Reset everything.
tf.reset_default_graph()
batch_size = 100
learning_rate = 0.5
training_epochs = 3
logs_path = '/log/TensorBoard'

# Load data set.
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets('/NMD/nmd/NMD.zip', one_hot = True)

# Get images.
with tf.name_scope('input'):
    x = tf.placeholder(tf.float32, shape = [None, 784], name = 'x-input')
    y_ = tf.placeholder(tf.float32, shape = [None, 10], name = 'y-input')

with tf.name_scope("weights"):
    W = tf.Variable(tf.zeros([784, 10]))

with tf.name_scope("biases"):
    b = tf.Variable(tf.zeros([10]))

with tf.name_scope("softmax"):
    y = tf.nn.softmax(tf.matmul(x, W) + b)

# Cost function
with tf.name_scope('cross_entropy'):
    cross_entropy = tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y), reduction_indices=[1]))

# Specify optimizer
with tf.name_scope('train'):
    train_op = tf.train.GradientDescentOptimizer(learning_rate).minimize(cross_entropy)

with tf.name_scope('Accuracy'):
    correct_prediction = tf.equal(tf.argmax(y, 1), tf.argmax(y_, 1))
    accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

# Create a summary.
tf.summary.scalar("cost", cross_entropy)
tf.summary.scalar("accuracy", accuracy)

summary_op = tf.summary.merge_all()

with tf.Session() as sess:
    sess.run(tf.initialize_all_variables())
    writer = tf.summary.FileWriter(logs_path, graph = tf.get_default_graph())

    for epoch in range(training_epochs):
        batch_count = int(mnist.train.num_examples / batch_size)
        for i in range(batch_count):
            batch_x, batch_y = mnist.train.next_batch(batch_size)
            _, summary = sess.run([train_op, summary_op], feed_dict = {x: batch_x, y_: batch_y})
            writer.add_summary(summary, epoch * batch_count + i)
        if epoch % 5 == 0:
            print('Epoch: ', epoch)
    print('Accuracy: ', accuracy.eval(feed_dict = {x: mnist.test.images, y_: mnist.test.labels}))
    print('Done')