# Скрипт для обучения SVM на данных Churn_Modelling
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVC
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, confusion_matrix, classification_report


def load_and_preprocess(csv_path):
    # CSV в репозитории разделён точкой с запятой
    df = pd.read_csv(csv_path, sep=';')

    # Выберем информативные признаки и таргет
    # Удалим служебные колонки
    drop_cols = ['RowNumber', 'CustomerId', 'Surname']
    df = df.drop(columns=[c for c in drop_cols if c in df.columns], errors='ignore')

    # Целевая переменная
    y = df['Exited']

    # Категориальные признаки: Geography -> one-hot, Gender -> бинарное
    X = df.drop(columns=['Exited'])

    if 'Gender' in X.columns:
        X['Gender'] = X['Gender'].map({'Male': 1, 'Female': 0})

    if 'Geography' in X.columns:
        geo_dummies = pd.get_dummies(X['Geography'], prefix='Geo', drop_first=True)
        X = pd.concat([X.drop(columns=['Geography']), geo_dummies], axis=1)

    # Убедимся, что остались только числовые признаки
    X = X.select_dtypes(include=[np.number])

    return X, y


def train_and_evaluate(csv_path):
    X, y = load_and_preprocess(csv_path)

    # Разделим данные
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33, random_state=42, stratify=y)

    # Масштабирование
    scaler = StandardScaler()
    X_train_scaled = scaler.fit_transform(X_train)
    X_test_scaled = scaler.transform(X_test)

    # Обучим SVM (RBF) с уравновешиванием классов
    svm = SVC(kernel='rbf', C=1.0, gamma='scale', class_weight='balanced', random_state=42)
    svm.fit(X_train_scaled, y_train.values.ravel())

    # Предсказания
    y_pred_test = svm.predict(X_test_scaled)
    y_pred_train = svm.predict(X_train_scaled)

    # Метрики (zero_division=0 — чтобы избежать UndefinedMetricWarning)
    metrics = {
        'train_accuracy': accuracy_score(y_train, y_pred_train),
        'test_accuracy': accuracy_score(y_test, y_pred_test),
        'test_precision': precision_score(y_test, y_pred_test, zero_division=0),
        'test_recall': recall_score(y_test, y_pred_test, zero_division=0),
        'test_f1': f1_score(y_test, y_pred_test, zero_division=0),
        'confusion_matrix': confusion_matrix(y_test, y_pred_test)
    }

    print('\nSVM (RBF) results:')
    print(f"Train accuracy: {metrics['train_accuracy']:.4f}")
    print(f"Test accuracy : {metrics['test_accuracy']:.4f}")
    print(f"Test precision: {metrics['test_precision']:.4f}")
    print(f"Test recall   : {metrics['test_recall']:.4f}")
    print(f"Test f1       : {metrics['test_f1']:.4f}")
    print('\nConfusion matrix (test):')
    print(metrics['confusion_matrix'])

    print('\nClassification report (test):')
    print(classification_report(y_test, y_pred_test, zero_division=0))