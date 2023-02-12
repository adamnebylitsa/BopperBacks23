import os
import tensorflow as tf
from model_setup import create_model, get_latest_model_path
#First, load model most recent model
#Create link between Python script and Minecraft mod
if __name__ == "__main__":
    #Initialize empty model
    model = create_model()
    #Obtain latest model from training model folder
    checkpoint_path = get_latest_model_path(os.getcwd() + "/training/models/")
    #If there is a .ckpt file in the provided path, then we load the weights
    if(checkpoint_path):
        model.load_weights(checkpoint_path)
    #Get data from Wii remote + nunchuck via piping
    #test_data = 
    #Obtain prediction
    #prediction = model.predict()
    #Send prediction to Minecraft mod via piping