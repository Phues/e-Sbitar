
import pandas as pd

# Original dataset by: https://www.kaggle.com/datasets/itachi9604/disease-symptom-description-dataset

input_file = 'dataset.csv'  
output_file = 'symptoms_cleaned.csv'  

# Read the input CSV file into a pandas DataFrame
df = pd.read_csv(input_file)

# Convert float values to strings in symptom columns
df[['Symptom_{}'.format(i) for i in range(1, 18)]] = df[['Symptom_{}'.format(i) for i in range(1, 18)]].astype(str)

# Concatenate the values of multiple columns into a single column
df['Symptoms'] = df.apply(lambda row: ','.join(row['Symptom_{}'.format(i)] for i in range(1, 18)), axis=1)

# Drop the original symptom columns if needed
df.drop(columns=['Symptom_{}'.format(i) for i in range(1, 18)], inplace=True)

# Drop duplicate rows based on the "Diseases" column
df_unique = df.drop_duplicates(subset='Disease', keep='first')

# Write the unique rows to the output CSV file
df_unique.to_csv(output_file, index=False, sep='.')

print("Duplicate diseases removed and unique diseases written to", output_file)


