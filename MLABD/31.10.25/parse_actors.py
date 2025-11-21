import ast
from collections import Counter

import pandas as pd
from sklearn.preprocessing import MultiLabelBinarizer

def parse_actors(actors_str):
    """
    Парсит строковое представление списка актёров.
    Возвращает Python-список актёров.
    """

    if pd.isna(actors_str):
        return []
    
    cleaned_actors_str = str(actors_str).replace("u'", "'").replace('u"', '"').strip()

    try:
        parsed = ast.literal_eval(cleaned_actors_str)
        if isinstance(parsed, (list, tuple)):
            return [str(actor).strip() for actor in parsed]
        else:
            return [str(parsed).strip()]
    except Exception:
        try:
            temp = cleaned_actors_str.strip()
            if temp.startswith('[') and temp.endswith(']'):
                inner = temp[1:-1]
            else:
                inner = temp
            
            for sep in ["', '", '", "', '", \'', "\', \""]:
                inner = inner.replace(sep, '||SEP||')
            parts = [part.strip(" '\"") for part in inner.split('||SEP||') if part.strip(" '\"")]
            return parts
        except Exception:
            return [cleaned_actors_str]