import tensorflow as tf
import json

def create_model():
    model = tf.keras.models.Sequential([
        tf.keras.layers.Flatten(input_shape=(28, 28), name='layers_flatten'),
        tf.keras.layers.Dense(512, activation='relu', name='layers_dense'),
        tf.keras.layers.Dropout(0.2, name='layers_dropout'),
        tf.keras.layers.Dense(10, activation='softmax', name='layers_dense_2')
    ])
    return model

def main():
    model = create_model()
    #Save model to local file system