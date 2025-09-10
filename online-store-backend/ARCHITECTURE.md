# Архитектура Online Store Backend

## Обзор

Этот проект представляет собой полностью рефакторированный бекенд для интернет-магазина, построенный с использованием
принципов Clean Code, объектно-ориентированного программирования и современных Spring технологий.

## Структура архитектуры

### 1. Базовые классы (Base Classes)

#### BaseEntity

- Абстрактный базовый класс для всех сущностей
- Содержит общие поля: `id`
- Использует `@MappedSuperclass` для JPA
- Lombok аннотации для автоматической генерации методов

#### AuditableEntity

- Наследует от `BaseEntity`
- Добавляет поля аудита: `createdAt`, `updatedAt`
- Автоматическое заполнение через `@CreatedDate` и `@LastModifiedDate`

### 2. Репозитории (Repositories)

#### BaseRepository

- Интерфейс с общими CRUD операциями
- Наследует от `JpaRepository`
- Содержит методы для пагинации, поиска, сохранения, удаления

### 3. Сервисы (Services)

#### BaseService

- Интерфейс с общими операциями для сервисов
- Содержит методы для CRUD операций с пагинацией

#### BaseServiceImpl

- Абстрактная реализация `BaseService`
- Содержит общую логику для всех сервисов
- Использует транзакции

### 4. Контроллеры (Controllers)

#### BaseController

- Абстрактный базовый контроллер
- Содержит общие эндпоинты для CRUD операций
- Поддерживает пагинацию
- Использует MapStruct для маппинга

### 5. DTO и Мапперы

#### BaseDto

- Базовый класс для всех DTO
- Содержит общее поле `id`

#### AuditableDto

- Наследует от `BaseDto`
- Добавляет поля аудита

#### BaseMapper

- Интерфейс для маппинга между сущностями и DTO
- Содержит методы для конвертации списков и страниц
- Реализуется конкретными мапперами с MapStruct

### 6. Валидация

#### Spring Validation

- Использует стандартные аннотации Bean Validation
- `@NotNull`, `@NotBlank`, `@Email`, `@Size`, `@Pattern`, `@DecimalMin`, `@Digits`
- Встроенная обработка ошибок валидации

### 7. Обработка исключений

#### GlobalExceptionHandler

- Централизованная обработка исключений
- Поддерживает различные типы ошибок
- Возвращает структурированные ответы
- Обработка ошибок валидации

#### ErrorResponse

- Стандартизированный формат ответов об ошибках
- Содержит детали ошибок валидации

### 8. Утилиты

#### EntityUtils

- Утилиты для работы с сущностями
- Извлечение ID, проверка состояния

#### PageUtils

- Утилиты для работы с пагинацией
- Создание Pageable объектов

### 9. Константы

#### ApiConstants

- Общие константы API
- Пути, размеры страниц, сообщения

## Модели данных

### Основные сущности (наследуют от AuditableEntity)

- `User` - пользователи
- `Product` - товары
- `Order` - заказы
- `Category` - категории
- `DeliveryAddress` - адреса доставки
- `BalanceTransaction` - транзакции баланса
- `Cart` - корзина

### Вспомогательные сущности (наследуют от BaseEntity)

- `OrderItem` - элементы заказа
- `ProductImage` - изображения товаров
- `CartItem` - элементы корзины

## Принципы архитектуры

### 1. DRY (Don't Repeat Yourself)

- Общая логика вынесена в базовые классы
- Переиспользование кода через наследование

### 2. SOLID принципы

- **S** - Single Responsibility: каждый класс имеет одну ответственность
- **O** - Open/Closed: открыт для расширения, закрыт для модификации
- **L** - Liskov Substitution: подклассы могут заменять базовые классы
- **I** - Interface Segregation: интерфейсы разделены по функциональности
- **D** - Dependency Inversion: зависимость от абстракций, а не от конкретных реализаций

### 3. Clean Code

- Понятные имена классов и методов
- Комментарии на русском языке
- Единообразное форматирование

### 4. Масштабируемость

- Легко добавлять новые сущности
- Общие операции доступны из коробки
- Гибкая система валидации

## Преимущества рефакторинга

1. **Переиспользование кода** - общая логика вынесена в базовые классы
2. **Единообразие** - все сущности следуют единым паттернам
3. **Легкость поддержки** - изменения в базовых классах влияют на все сущности
4. **Масштабируемость** - легко добавлять новые функции
5. **Тестируемость** - четкое разделение ответственности
6. **Читаемость** - понятная структура и именование

## Использование

### Создание новой сущности

1. Создать модель, наследующую от `BaseEntity` или `AuditableEntity` с Spring Validation
2. Создать репозиторий, наследующий от `BaseRepository`
3. Создать сервис, наследующий от `BaseServiceImpl`
4. Создать DTO с валидацией
5. Создать маппер с MapStruct
6. Создать контроллер, наследующий от `BaseController`

### Пример

```java
// 1. Модель с валидацией
@Entity
@Table(name = "my_entities")
public class MyEntity extends AuditableEntity {
  @NotBlank(message = "Name is required")
  @Size(max = 100, message = "Name must not exceed 100 characters")
  private String name;

  @NotNull(message = "Value is required")
  @DecimalMin(value = "0.0", message = "Value must be non-negative")
  private BigDecimal value;
}

// 2. Репозиторий
@Repository
public interface MyEntityRepository extends BaseRepository<MyEntity, Long> {
  // специфичные методы
}

// 3. Сервис
@Service
public class MyEntityService extends BaseServiceImpl<MyEntity, Long> {
  @Override
  protected BaseRepository<MyEntity, Long> getRepository() {
    return myEntityRepository;
  }
}

// 4. DTO с валидацией
public class MyEntityDto extends AuditableDto {
  @NotBlank(message = "Name is required")
  @Size(max = 100, message = "Name must not exceed 100 characters")
  private String name;

  @NotNull(message = "Value is required")
  @DecimalMin(value = "0.0", message = "Value must be non-negative")
  private BigDecimal value;
}

// 5. Маппер с MapStruct
@Mapper(componentModel = "spring")
public interface MyEntityMapper extends BaseMapper<MyEntity, MyEntityDto> {
  // MapStruct автоматически сгенерирует реализацию
}

// 6. Контроллер
@RestController
@RequestMapping("/api/v1/my-entities")
public class MyEntityController extends BaseController<MyEntity, MyEntityDto, Long> {
  @Override
  protected BaseService<MyEntity, Long> getService() {
    return myEntityService;
  }

  @Override
  protected BaseMapper<MyEntity, MyEntityDto> getMapper() {
    return myEntityMapper;
  }
}
```

### Преимущества нового подхода

1. **Spring Validation** - стандартные аннотации валидации
2. **MapStruct** - автоматическая генерация мапперов
3. **Меньше кода** - убраны кастомные валидаторы и мапперы
4. **Лучшая производительность** - MapStruct генерирует код на этапе компиляции
5. **Типобезопасность** - ошибки видны на этапе компиляции

## Заключение

Данная архитектура обеспечивает высокую степень переиспользования кода, легкость поддержки и масштабируемость. Все
компоненты следуют единым принципам и паттернам, что делает код предсказуемым и понятным для разработчиков.
