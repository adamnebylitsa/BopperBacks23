import tensorflow as tf
import datetime, os, sys
from model_setup import create_model, get_latest_model_path
import numpy as np

def test_model(dataFilepath, dataLabel):
    #Load training data and set training label
    testing_data = np.loadtxt(dataFilepath, delimiter=",")
    #Set training labels to be the same value (passed in as function argument)
    testing_labels = np.full((testing_data.shape[0],), dataLabel)
    #Initialize model and load last checkpoint
    model = create_model()
    #Set model to process input data of the shape of training_data
    model.add(tf.keras.Input(testing_data[0].shape))
    #Add layers to model because??? (Kahlil please verify)
    model.add(tf.keras.layers.Dense(8))
    #Obtain latest model from training model folder
    checkpoint_path = get_latest_model_path(os.getcwd() + "/training/models/")
    #If there is a .ckpt file in the provided path, then we load the weights
    if(checkpoint_path):
        model.load_weights(checkpoint_path)
    #Determine file directory file to write results out to
    current_time = datetime.datetime.now()
    script_dir = os.path.dirname(__file__)
    rel_path = 'testing/output/Results.txt'
    abs_file_path = os.path.join(script_dir, rel_path)
    file = open(abs_file_path,"a")
    #Copute the loss, accuracy and write these values to Results.txt
    loss, acc = model.evaluate(testing_data, testing_labels, verbose = 2)
    file.write(current_time.strftime("%m-%d-%y-%H-%M-%S "))
    file.write("Accuracy Percentage: " +str(round(acc * 100, 3))+"%, Loss: " + str(loss) + "\n")
    file.close()
    return

#Run test_model() if running test.py from command line
if __name__ == "__main__":
    #Pass in absolute filepath of testing data as first argument
    training_data_filepath = sys.argv[1]
    #Pass in testing label as second argument
    training_label = sys.argv[2]
    test_model(training_data_filepath, training_label)