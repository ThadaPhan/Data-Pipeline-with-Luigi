from time import sleep
from datetime import datetime
import os
import csv


def WriteToCSV(header, data, pathToFile):
    with open(pathToFile, 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(header)
        writer.writerows(data)


def ExecTerminalCommand(command):
    os.system(command)



def GeneralFromTime():
    now = datetime.now()

    hourDayMonth = now.strftime("%H%d%m")
    minuteSecond = now.strftime("%M%S")

    path = 'data/' + now.strftime("%Y/%m/%d/%H/")

    fileName = minuteSecond + '.csv'

    data = [
            ['101' + hourDayMonth, 'Mary Jane ' + hourDayMonth, 'Female', 30],
            ['102' + hourDayMonth, 'Peter Parker ' + hourDayMonth, 'Male', 29]
    ]

    return data, minuteSecond, path, fileName


def SleepUntilNextHour():
    sleep(3600 - datetime.now().second - datetime.now().minute * 60)


if __name__ == "__main__":

    header = ['Id', 'Name', 'Gender', 'Age']

    SleepUntilNextHour()

    while True:

        data, minute_second, path, fileName = GeneralFromTime()

        ExecTerminalCommand('mkdir -p ' + path)

        WriteToCSV(header, data, path + fileName)

        ExecTerminalCommand('hdfs dfs -mkdir -p /' + path)

        ExecTerminalCommand('hdfs dfs -put ' + path + fileName + ' ' + '/' + path)

        SleepUntilNextHour()

