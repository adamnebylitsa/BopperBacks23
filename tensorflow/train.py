import tensorflow as tf
import sys, os, glob
from model_setup import create_model
import numpy as np

#Helper function for loading the filename of the latest .ckpt file
def get_latest_model_path(folder_path:str):
    list_of_files = glob.glob(folder_path + '/*') # * means all if need specific format then *.csv
    if(len(list_of_files) < 1):
        return None
    latest_file = max(list_of_files, key=os.path.getctime)
    return latest_file

def train_model(data_filepath:str, training_label:str):
    #Load training data and set training label
    training_data = np.loadtxt(data_filepath, delimiter=",")
    #Set training labels to be the same value (passed in as function argument)
    training_labels = np.full((training_data.shape[0],), training_label)
    #Initialize empty model
    model = create_model()
    #Set model to process input data of the shape of training_data
    model.add(tf.keras.Input(training_data[0].shape))
    #Add layers to model because??? (Kahlil please verify)
    model.add(tf.keras.layers.Dense(8))
    #Load weights into model from last checkpoint
    checkpoint_path = get_latest_model_path(os.getcwd() + "/training/models/")
    #If there is a .ckpt file in the provided path, then we load the weights
    if(checkpoint_path):
        model.load_weights(checkpoint_path)
    #Create new checkpoint to write newly-computed weights to
    new_checkpoint_path = ""
    if(checkpoint_path):
        model_iteration = checkpoint_path.split("/training/models/model")
        new_checkpoint_path = os.getcwd() + "training/models/model" + model_iteration
    #If an existing checkpoint does not exist, then create the first model 1
    else:
        new_checkpoint_path = os.getcwd() + "/training/models/model1.ckpt"
    #Instantiate ModelCheckpoint to track newly-computed weights
    cp_callback = tf.keras.callbacks.ModelCheckpoint(filepath=new_checkpoint_path,
                                                 save_weights_only=True,
                                                 verbose=1)
    # Train the model with the new callback (ie. update the model's weights in the .ckpt file)
    model.fit(training_data, 
            training_labels,  
            epochs=10,
            callbacks=[cp_callback])
    # This may generate warnings related to saving the state of the optimizer.
    # These warnings (and similar warnings throughout this notebook)s
    # are in place to discourage outdated usage, and can be ignored.

#Run train_model() if running train.py from command line
if __name__ == "__main__":
    #Pass in absolute training data filepath as first argument
    training_data_filepath = sys.argv[1]
    #Pass in training label as second argument
    training_label = sys.argv[2]
    train_model(training_data_filepath, training_label)