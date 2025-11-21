# mLABD
Репозиторий для хранения работ по предмету "МОиБД".

---
## Используемые Python-пакеты:
- Seaborn

## Устранение предупреждения sklearn: "X does not have valid feature names..."
Предупреждение появляется, когда модель (например, KNeighborsClassifier) обучена на pandas.DataFrame (с именами признаков), а при predict передаётся массив (list/ndarray) без имен.

### Вариант 1 (рекомендован)
Обучать и предсказывать на DataFrame с одинаковыми столбцами:
```python
import pandas as pd
from sklearn.neighbors import KNeighborsClassifier

# Обучение
X_train_df = pd.DataFrame(X_train, columns=["f1","f2","f3"])
model = KNeighborsClassifier()
model.fit(X_train_df, y_train)

# Предсказание (обязательно DataFrame с теми же колонками)
X_new_df = pd.DataFrame(X_new, columns=model.feature_names_in_)
y_pred = model.predict(X_new_df)
```

### Вариант 2
Убрать имена при обучении, если дальше работаете только с массивами:
```python
model = KNeighborsClassifier()
model.fit(X_train_df.values, y_train)  # .values убирает имена -> предупреждение исчезнет
y_pred = model.predict(X_new)          # X_new может быть ndarray
```

### Проверка
```python
print(model.feature_names_in_)  # убедиться какие имена зафиксированы
```

### Частые ошибки
- Передача списка вместо DataFrame после обучения на DataFrame.
- Перестановка или удаление столбцов без пересоздания DataFrame.
- Разное количество признаков между fit и predict.

Выберите один стиль (везде DataFrame или везде ndarray) и соблюдайте его.
