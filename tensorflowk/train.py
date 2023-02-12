import tensorflow as tf
from keras import Sequential
from keras.layers import Flatten, Dense, Dropout, BatchNormalization
from keras.layers import Conv2D, MaxPool2D
from keras.optimizers import Adam
print(tf.__version__)