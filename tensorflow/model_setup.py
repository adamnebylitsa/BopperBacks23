import tensorflow as tf
import glob, os
def create_model():
    model = tf.keras.models.Sequential()
    return model
#Loads the filename of the latest .ckpt file
def get_latest_model_path(folder_path:str):
    list_of_files = glob.glob(folder_path + '/*') # * means all if need specific format then *.csv
    if(len(list_of_files) < 1):
        return None
    latest_file = max(list_of_files, key=os.path.getctime)
    return latest_file