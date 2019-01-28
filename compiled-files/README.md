# Как запустить проект?

## Требования для запуска
- Java Developer Kit 8 with at least Update 40 (скачать можно [тут](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))

*Если есть желание самому сбилдить проект, то кликай [сюда](https://github.com/javafx-maven-plugin/javafx-maven-plugin)*


## Запуск консольной версии
Скомпилированная консольная версия приложения лежит в файле **consoleApp-1.0-SNAPSHOT-jar-with-dependencies.jar**

Чтобы запустить, откройте терминал в папке с данный файлом и введите команду вида:
```
java -jar consoleApp-1.0-SNAPSHOT-jar-with-dependencies.jar [args]
```

**Пример:**

```
java -jar consoleApp-1.0-SNAPSHOT-jar-with-dependencies.jar -i 6 -i 8 -e min
 Inserting 6
 Inserting 8
 Min number is 6
 ```

Подробную документацию по консольному приложению можно посмотреть [тут](https://github.com/BaLiKfromUA/project_advance_1/blob/master/consoleApp/README.md).

## Установка оконного приложения
Для установки визуализации на Windows воспользуйтесь установщиком 
```
LeftistHeap-{last-version}-SNAPSHOT-{last-version}.msi
```
**Последняя версия - 1.0**

*Экзешник не советую запускать, т.к он не протестирован (:*

**Enjoy!**

