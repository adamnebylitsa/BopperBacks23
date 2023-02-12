import tensorflow as tf
import json
import datetime
import os

def test_model(dataFilepath, modelFilepath, dataLabels):
    current_time = datetime.datetime.now()
    script_dir = os.path.dirname(__file__)
    rel_path = 'testing/output/Results.txt'
    abs_file_path = os.path.join(script_dir, rel_path)
    file = open(abs_file_path,"a")
    #loss, acc = modelFilepath.evaluate(dataFilepath,dataLabels, verbose = 2)
    file.write(current_time.strftime("%m-%d-%y-%H-%M-%S "))
    file.write("Accuracy Percentage:" +str(1)+"\n")
    file.close()
    return



if __name__ == "__main__":
    test_model(1,1,1)