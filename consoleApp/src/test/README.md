# Тестирование

## Предисловие
Все тесты описанные в этом разделе выполнены с помощью фреймворка JUnit4. 
**Почему?**
Потому что это удобнее и даёт более правдивую информацию, 
т.к мой самописный тестер обрабатывает строки, а это очень дорого в Java. 

## Где файлы, Лебовски?
Чтобы не заставлять тестировщика разбираться с джавой, некоторые тестовые файлы я всетаки сделал(глянуть [тут](https://github.com/BaLiKfromUA/project_advance_1/tree/master/consoleApp/tests)).
В этих файлах лежат тесты для самой структуры(левосторонней кучи), чтобы тестер мог их запустить в консольном приложении.
Все остальные тесты(тесты парсера, команд, самого тестера) в файлах отсутствуют, но будут описаны.

## Тесты левосторонней кучи
Все тесты можно разделить на **5** групп по **размеру** и на **13** по **типу**.  
Начнем с разделения по **размеру**:
- **Small** - количество тестовых элементов **500**
- **Medium** - количество тестовых элементов **5000**
- **Big** - количество тестовых элементов **50000**
- **Large** - количество тестовых элементов **500000**
- **Extra-large** - количество тестовых элементов **5000000**

Для каждого размера выполняются **13** видов тестов, которые будут описаны ниже.

### 1) Compare with PriorityQueue
Java предоставляет свою реализацию приоритетной очереди в пакете Collections. Насколько мне известно, под капотом
там бинарная куча. Так что можем сравнить результаты одних и тех же операций в нашей куче и куче джавы:)
Для этого я решил запустить цикл от **0** до **size-1**, где size зависит от типа теста по размеру.
На каждом шаге цикла мы добавляем текущий индекс в наши кучи и проверяем минимальный элемент. Как можно догадаться, он должен быть одинаков:)
После этого, мы пошагово опустошаем обе кучи и после каждого шага проверяем минимальный элемент в каждой куче.
Ну и по окончанию проверяем что обе кучи пусты. 
Исходный код теста представлен ниже:
```
    @Test
    @DisplayName("Compare with priority queue Test")
    public void insertCompareWithPriorityQueue() {
        PriorityQueue<Integer> javaPriorityQueue = new PriorityQueue<>();
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            final int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            javaPriorityQueue.add(randomValue);
            heap.insert(randomValue);
            assertEquals("Minimums in heaps should be equal:", (int) javaPriorityQueue.peek(), heap.getMin());
        }

        for (int i = 0; i < size; ++i) {
            assertEquals("Minimums in heaps should be equal:", (int) javaPriorityQueue.peek(), heap.getMin());
            javaPriorityQueue.poll();
            heap.extractMin();
        }

        assertEquals("Heaps should be empty", heap.isEmpty(), javaPriorityQueue.isEmpty());
    }
    
```

**Данный тест показывает что наша самописная очередь идейно работает верно.**

### 2) Insert negative increasing numbers
Данный тест являеться первым из семейства тестов с убывающими/возврастающими последовательностями.
Что мы делаем? Мы добавляем в кучу элементы начиная с **-(size-1)** до **0**. И после каждого добавления
проверяем минимальный элемент. Очевидно, что это всегда должен быть **-(size-1)**. После добавления всех элементов
мы опустошаем кучу в массив и проверяем что элементы в правильном порядке(по возрастанию).
Исходный код теста представлен ниже:
```
    @Test
    @DisplayName("Increasing negative elements test")
    public void insertNegativeIncreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = size-1; i >= 0; --i) {
            heap.insert(-i);
            expected.add(-i);
            assertEquals("Min should be -size:", -(size-1), heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
    
``` 

**Данный тест (как и следующий) показывает что куча правильно работает с отрицательными числами,
и правильно их возвращает.**
### 3) Insert decreasing negative numbers
Похожий тест, но теперь числа вводяться в порядке от **0** до **-(size-1)**. И минимальным после каждого шага будет 
последний добавленный элемент.
После ввода, извлекаем все элементы из кучи и проверяем их порядок(должно быть по возрастанию).
Исходный код теста: 
```
    @Test
    @DisplayName("Decreasing negative elements test")
    public void insertNegativeDecreasing(){
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            heap.insert(-i);
            expected.add(-i);
            assertEquals("Min should be -index:", -i, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

```
### 4) Insert increasing positive numbers
Вводим числа в порядке от **0** до **size-1**. На каждом шаге проверяем минимальный элемент(всегда должен быть 0).
После ввода, извлекаем все элементы из кучи и проверяем их порядок(должно быть по возрастанию).
Исходный код теста:
```
    @Test
    @DisplayName("Increasing positive elements test")
    public void insertIncreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            expected.add(i);
            assertEquals("Min should be 0:", 0, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
```

**Данный тест (как и следующий) показывает что куча правильно работает с положительными числами,
и правильно их возвращает.**

### 5) Insert decreasing positive numbers
Похожий тест, но теперь числа вводяться в порядке от **size-1** до **0**. И минимальным после каждого шага будет 
последний добавленный элемент. После ввода, извлекаем все элементы из кучи и проверяем их порядок(должно быть по возрастанию).
Исходный код теста: 
```
    @Test
    @DisplayName("Decreasing positive elements test")
    public void insertDecreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = size; i > 0; --i) {
            heap.insert(i);
            expected.add(i);
            assertEquals("Min should be current index:", i, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

```

### 6) Insert zero
Добавляем 0 **size** раз. 
После каждого раза проверяем ноль ли минимум:)
Так же потом извлекаем все нули и проверяем не потеряли ли что-то.
Исходный код теста:
```
    @Test
    @DisplayName("Zero Test")
    public void insertZero() {
        Integer[] expectedArray = new Integer[size];
        Arrays.fill(expectedArray, 0);

        for (int i = 0; i < size; ++i) {
            heap.insert(0);
            assertEquals("Min should be 0:", 0, heap.getMin());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
```
**Данный тест проверяет правильно ли работает наша куча с одинаковыми элементами**

### 7) Insert same random
Такая же механика как и в **6** тесте, только вставляем один и тот же **случайный 
элемент**.
Исходный код теста:
```
    @Test
    @DisplayName("Same Random Test")
    public void insertOneRandomNumber() {
        RANDOM.setSeed(System.currentTimeMillis());
        final int randomNumber = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        Integer[] expectedArray = new Integer[size];
        Arrays.fill(expectedArray, randomNumber);

        for (int i = 0; i < size; ++i) {
            heap.insert(randomNumber);
            assertEquals("Min should be your random number:", randomNumber, heap.getMin());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
    
```

### 8) Insert-extract
Добавляем в кучу элементы от **0** до **size-1**. После каждого добавления проверяем минимум и извлекаем его.
Минимум на каждом шаге должен быть последним добавленным элементом. Так же после каждого добавления и в конце теста
проверяем пуста ли куча.
Исходный код теста:
```
    @Test
    @DisplayName("Insert-extract test")
    public void insertExtract() {
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.extractMin();
            assertTrue("Heap should be empty:", heap.isEmpty());
        }

        assertEquals("Min should be -1:", -1, heap.getMin());
        assertTrue("Heap should be empty:", heap.isEmpty());
    }
```
**Данный тест проверяет правильно ли добавление и извлечение элемента взаимодействуют вместе.
Так же мы проверяем не осталось ли что-то в куче после удаления всех элементов**

### 9) Get min test
Добавляем случайный элемент в кучу и **size** раз проверяем минимум в куче. 
Исходный код теста:
```
    @Test
    @DisplayName("GetMin Test")
    public void getMinFromOneElementHeap() {
        RANDOM.setSeed(System.currentTimeMillis());
        final int randomNumber = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        heap.insert(randomNumber);
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be your random number:", randomNumber, heap.getMin());
        }
    }
```
**Данный тест проверяет отсутствие влияния вывода минимума на элементы в куче.**
