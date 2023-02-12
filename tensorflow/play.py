import os
import tensorflow as tf
from model_setup import create_model, get_latest_model_path
import zmq
import numpy as np

def create_connection():
    #Create context to create Socket with port 54000
    context = zmq.Context()
    socket = context.Socket(zmq.REQ)
    socket.connet("tcp://localhost:54000")
    return socket

def initialize_model():
    model = tf.Sequential()
    #Harcode path for now
    checkpoint_path = os.getcwd() + "PROVIDE VALUE PLEASE"
    #If there is a .ckpt file in the provided path, then we load the weights
    if(checkpoint_path):
        model.load_weights(checkpoint_path)
    return model

def predict_movement(model, input_data):
    input_data = np.array(input_data)
    pred = model.predict(input_data)

#First, load model most recent model
#Create link between Python script and Minecraft mod
if __name__ == "__main__":
    #Initialize empty model
    model = create_model()
    #Obtain latest model from training model folder
    #PROVIDE DIFFERENT PATH
    checkpoint_path = get_latest_model_path(os.getcwd() + "/training/models/")
    #If there is a .ckpt file in the provided path, then we load the weights
    if(checkpoint_path):
        model.load_weights(checkpoint_path)
    #Create socket connection to read data from localhost:54000
    socket = create_connection()
    #Repeatedly loop and listen for data from localhost:54000
    while True:
        try:
            #Receive data from socket
            data = socket.recv()
            #Model computes predicted movement. Ideally, predict_movement() returns an array containing
            #a single string, which we would extract to write via the socket as a text value
            prediction = predict_movement(model, data)
            socket.send(prediction)
        except RuntimeError:
            print("Nope :(")