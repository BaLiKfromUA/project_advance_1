# Консольная версия проекта
## Тема
Левосторонняя куча
## Содержание
1. [Основной функционал](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D0%BD%D0%BE%D0%B9-%D1%84%D1%83%D0%BA%D0%BD%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB)
2. [Синтаксис команд](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D1%81%D0%B8%D0%BD%D1%82%D0%B0%D0%BA%D1%81%D0%B8%D1%81-%D0%BA%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4)
3. [Примеры использования](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D1%8B-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F)
   - [insert & extract](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#insert--extract)
   - [file](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#file)
   - [test](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#test)
4. [Сообщения об ошибках](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D1%8F-%D0%BE%D0%B1-%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%D1%85)
5. [Тесты](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D1%82%D0%B5%D1%81%D1%82%D1%8B)
   - [Формат ввода](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82-%D0%B2%D0%B2%D0%BE%D0%B4%D0%B0)
   - [Сообщения тестера](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D1%8F-%D1%82%D0%B5%D1%81%D1%82%D0%B5%D1%80%D0%B0)
   - [Примеры](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D1%8B)
     - [Пройденный тест](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D0%BF%D1%80%D0%BE%D0%B9%D0%B4%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9-%D1%82%D0%B5%D1%81%D1%82)
     - [Непройденный тест](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D0%BD%D0%B5%D0%BF%D1%80%D0%BE%D0%B9%D0%B4%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9-%D1%82%D0%B5%D1%81%D1%82)
     - [Неправильно указано расположение файла](https://github.com/BaLiKfromUA/project_advance_1/tree/Sanya/consoleApp#%D0%BD%D0%B5%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D1%8C%D0%BD%D0%BE-%D1%83%D0%BA%D0%B0%D0%B7%D0%B0%D0%BD%D0%BE-%D1%80%D0%B0%D1%81%D0%BF%D0%BE%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5-%D1%84%D0%B0%D0%B9%D0%BB%D0%B0)   
## Основной фукнционал
1. Добавление числа в кучу
2. Извлечение минимального числа из кучи
3. Считывание команд 1 и 2 из файла
4. Поддержка пользовательских тестов из файла
## Синтаксис команд
| Команда                                            | Описание                                                   |
| ---------------------------------------------------| -----------------------------------------------------------|
| `-e, --extract=<extract>`                          | Извлекает минимальное число из кучи и выводит его на экран |
| `-f, --file=<fileParse>`                           | Считывает из файла команды и запускает их                  |
| `-h, --help`                                       | Выводит описание всех команд                               |
| `-i, --insert=<insert>[,<insert>...]`              | Добавляет число в кучу                                     |
| `-t, --test=<inputFile>,<answerFie>`               | Запускает пользовательские тесты из файла                  |
## Примеры использования
### insert & extract                                                         
```
-i 1 -i 313 -e min -e min -e min 
Inserting 1
Inserting 313
Min number is 1
Min number is 313
Heap is empty!
```
### file

__{path}/input.txt__
```
-i 41 -i 31 -e min -e min -i 1 
```

```
-f {path/input.txt}
Inserting 41
Inserting 31
Min number is 31
Min number is 41
Inserting 1
```
```
-f {path/input.txt} -i 22 -e min -e min
Inserting 41
Inserting 31
Min number is 31
Min number is 41
Inserting 1
Inserting 22 
Min number is 1
Min number is 22
```
### test

__Запрещается использовать вместе с другими командами!__ 

__{path}/test.txt__
```
1
2
3
min
extractMin
min
extractMin
min
extractMin
```

__{path}/answer.txt__
```
1
2
3
```
```
-t {path}/test.txt,{path}/answer.txt 
OK - Solution passed
```

## Сообщения об ошибках

| Сообщение                                                 | Описание                                                           |
|-----------------------------------------------------------|--------------------------------------------------------------------|
| Missing parameters in some methods. Use -h for help.      | В одной или более командах не хватает параметров                   |
| Unknown command                                           | Не команда                                                |
| Heap is empty!                                            | Возникает при команде `-e min` в случае, если куча пустая          |
| Min number is [min number]                                | Результат работы команды `-e min`. Выводит наименьшее число в куче |
| Inserting [number]                                        | Результат работы команды `-i [number]`. Выводит вводимое число     |
| File not found                                            | Выводится в случае, если по указаному пути файла не существует     |
| Can't resolve symbols                                     | Ошибка чтения файла                                                | 
| Option should be specified only once                      | Команда должна быть ипользована лишь единожды (для тестов и файлов)|
| Wrong parameter                                           | Некорректный аргумент команды                                      |
| There are no commands                                     | Выводится в случае отсутствия команд                               |
| Incorrect number of parameters                            | Некорректное число аргументов команды (для тестов)                 |

## Тесты
### Формат ввода
`min` - Выводится минимальное число, но __не удаляется__

`extractMin` - __Удаляется__ минимальное число

### Сообщения тестера
| Сообщение тестера              | Значение                                                  |
|--------------------------------|-----------------------------------------------------------|
| OK - Solution passed           | Программа верно работает на соответствующем наборе тестов |
| IE - Invalid input format      | Неверный формат ввода                                    |
| WA - Wrong answer              | Ответ неверен                                             |
| RE - Error reading input file  | Ошибка чтения файла ввода                                 |
| RE - Error reading answer file | Ошибка чтения файла с ответами                            |

### Примеры 
#### Пройденный тест

__{path}/corrertTest.txt__
```
1
2
3
min
extractMin
min
extractMin
min
extractMin
```

__{path}/answer.txt__
```
1
2
3
```
```
-t {path}/correctTest.txt,{path}/answer.txt 
OK - Solution passed
```

#### Непройденный тест

__{path}/incorrertTest.txt__
```
-1
1
2
min
extractMin
min
extractMin
min
extractMin
min
```

__{path}/answer.txt__
```
1
2
3
```
```
-t {path}/incorrectTest.txt,{path}/answer.txt 
WA - Wrong answer
```
#### Неправильно указано расположение файла

__{path}/validateTest.txt__
```
1
2
3
max
min
min
```

__{path}/answer.txt__
```
1
2
3
```
```
-t {path}/validateTest.txt,{path}/answer.txt 
IE - Invalid input format
```