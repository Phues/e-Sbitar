import pandas as pd
import json

input_file = 'symptoms_cleaned.csv'  
output_file = 'disease.json'  

# Read the input CSV file into a pandas DataFrame with dot as the delimiter
df = pd.read_csv(input_file, delimiter='.')

# Create a list to store the disease records
disease_records = []

# Load description data from the second CSV file
description_data = pd.read_csv('symptom_Description.csv')

# Create a dictionary to map disease names to descriptions
disease_description_map = dict(zip(description_data['Disease'], description_data['Description']))

# Add description to each disease object
df['Description'] = df['Disease'].map(disease_description_map)

# Iterate over each row in the DataFrame
for index, row in df.iterrows():
    disease_name = row['Disease']
    description = row['Description']
    symptoms = row['Symptoms'].split(',')
    # Remove "nan" values from the list
    symptoms = [symptom for symptom in symptoms if symptom != "nan"]
    
    
    # Create a dictionary for the disease record
    disease_record = {'disease': disease_name,'description': description, 'symptoms': symptoms}
    
    # Append the disease record to the list
    disease_records.append(disease_record)

# Convert the list of disease records to JSON
json_data = json.dumps(disease_records, indent=4)

# Write the JSON data to the output file
with open(output_file, 'w') as file:
    file.write(json_data)

print("CSV file converted to JSON format and written to", output_file)





