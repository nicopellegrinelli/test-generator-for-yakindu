import os 
import sys
import numpy as np
import pandas as pd
import seaborn as sns
import statistics
import matplotlib.pyplot as plt
from sklearn.cluster import OPTICS, DBSCAN, KMeans
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score, davies_bouldin_score

os.chdir(os.path.dirname(os.path.abspath(__file__)))

# Read the CSV file
X = pd.read_csv('Merged.csv')

standard = len(X)
standardPassed = len(X[X['StandardSCTUnitStatus'] == 'PASSED'])
standardFailed = len(X[X['StandardSCTUnitStatus'] == 'FAILED'])
standardErrors = len(X[X['StandardSCTUnitStatus'] == 'ERRORS'])
standardNotGenerated = len(X[X['StandardSCTUnitStatus'] == 'NOT GENERATED'])
standardBlocked = len(X[X['StandardSCTUnitStatus'] == 'BLOCKED'])

simplified = len(X)
simplifiedPassed = len(X[X['SimplifiedSCTUnitStatus'] == 'PASSED'])
simplifiedFailed = len(X[X['SimplifiedSCTUnitStatus'] == 'FAILED'])
simplifiedErrors = len(X[X['SimplifiedSCTUnitStatus'] == 'ERRORS'])
simplifiedNotGenerated = len(X[X['SimplifiedSCTUnitStatus'] == 'NOT GENERATED'])
simplifiedBlocked = len(X[X['SimplifiedSCTUnitStatus'] == 'BLOCKED'])

# Dropping irrelevant rows where 'SimplifiedSCTUnitStatus' is not 'PASSED' or 'FAILED'
X = X[(X['SimplifiedSCTUnitStatus'] == 'PASSED') | (X['SimplifiedSCTUnitStatus'] == 'FAILED')]

# Cast to int or float everything that should be a number
X['NumStates'] = X['NumStates'].astype(int)
X['AvgDepth'] = X['AvgDepth'].astype(float)
X['MaxDepth'] = X['MaxDepth'].astype(int)
X['StandardEvosuiteCoverage'] = X['StandardEvosuiteCoverage'].astype(float)
X['StandardSCTUnitCoverage'] = X['StandardSCTUnitCoverage'].astype(float)
X['SimplifiedEvosuiteCoverage'] = X['SimplifiedEvosuiteCoverage'].astype(float)
X['SimplifiedSCTUnitCoverage'] = X['SimplifiedSCTUnitCoverage'].astype(float)

# Collecting general data
print(f"Standard statechars ({standard} total)")
print(f"->Mean SCTUnit coverage: {statistics.mean(X['StandardSCTUnitCoverage'])}")
print(f"->Num. SCTUnit classes that passed: {standardPassed}")
print(f"->Num. SCTUnit classes that failed: {standardFailed}")
print(f"->Num. SCTUnit classes with errors: {standardErrors}")
print(f"->Num. SCTUnit classes not generated: {standardNotGenerated}")
print(f"->Num. SCTUnit classes blocked: {standardBlocked}")

# Collecting general data
print(f"Simplified statechars ({simplified} total)")
print(f"->Mean SCTUnit coverage: {statistics.mean(X['SimplifiedSCTUnitCoverage'])}")
print(f"->Num. SCTUnit classes that passed: {simplifiedPassed}")
print(f"->Num. SCTUnit classes that failed: {simplifiedFailed}")
print(f"->Num. SCTUnit classes with errors: {simplifiedErrors}")
print(f"->Num. SCTUnit classes not generated: {simplifiedNotGenerated}")
print(f"->Num. SCTUnit classes blocked: {simplifiedBlocked}")

# Calculating correlation coefficient
correlation = X['SimplifiedEvosuiteCoverage'].corr(X['SimplifiedSCTUnitCoverage'])
print(f"Correlation coefficient between SimplifiedEvosuiteCoverage and SimplifiedSCTUnitCoverage: {correlation}")
correlation = X['SimplifiedSCTUnitCoverage'].corr(X['NumStates'])
print(f"Correlation coefficient between SimplifiedSCTUnitCoverage and NumStates: {correlation}")
correlation = X['SimplifiedSCTUnitCoverage'].corr(X['AvgDepth'])
print(f"Correlation coefficient between SimplifiedSCTUnitCoverage and AvgDepth: {correlation}")
correlation = X['SimplifiedSCTUnitCoverage'].corr(X['MaxDepth'])
print(f"Correlation coefficient between SimplifiedSCTUnitCoverage and MaxDepth: {correlation}")

# Dropping specified columns
drop_features = [
    'StandardEvosuiteCoverage', 
    'StandardSCTUnitCoverage', 
    'StandardSCTUnitStatus', 
    'SimplifiedEvosuiteCoverage', 
    'SimplifiedSCTUnitStatus'
]
X = X.drop(drop_features, axis=1)

# Specify the columns you want to scale
columns_to_scale = ['NumStates', 'AvgDepth', 'MaxDepth']  # replace with your column names

# Create a copy of the DataFrame to avoid modifying the original
X_scaled = X.copy()

# Scale the specified columns
scaler = StandardScaler()
X_scaled[columns_to_scale] = scaler.fit_transform(X[columns_to_scale])

# Apply the OPTICS algorithm using the scaled columns
optics_model = OPTICS(xi=0.1, min_cluster_size=10, min_samples=4)
optics_model.fit(X_scaled[columns_to_scale])

# Apply the DBSCAN algorithm using the scaled columns
dbscan_model = DBSCAN(eps=0.55, min_samples=2)
dbscan_model.fit(X_scaled[columns_to_scale])

# Apply the KMeans algorithm using the scaled columns
kmeans_model = KMeans(n_clusters=4, random_state=0, n_init="auto")
kmeans_model.fit(X_scaled[columns_to_scale])

# Change labels according to improve readability a posteriori
for i in range(len(dbscan_model.labels_)):
    val = dbscan_model.labels_[i]
    if val == 4:
        dbscan_model.labels_[i] = 0 
    elif val == 1:
        dbscan_model.labels_[i] = 1 
    elif val == 0:
        dbscan_model.labels_[i] = 2
    elif val == 3:
        dbscan_model.labels_[i] = 3
    elif val == 2:
        dbscan_model.labels_[i] = 4

# Adding cluster labels to the original DataFrame
X['optics_label'] = optics_model.labels_
X['dbscan_label'] = dbscan_model.labels_
X['kmeans_label'] = kmeans_model.labels_

# Calculate silhouette score for each model
silhouette_optics = silhouette_score(X_scaled[columns_to_scale], optics_model.labels_)
silhouette_dbscan = silhouette_score(X_scaled[columns_to_scale], dbscan_model.labels_)
silhouette_kmeans = silhouette_score(X_scaled[columns_to_scale], kmeans_model.labels_)
print("PREFERE HIGH SILHOUTTE")
print(f"Silhouette Score (OPTICS): {silhouette_optics}")
print(f"Silhouette Score (DBSCAN): {silhouette_dbscan}")
print(f"Silhouette Score (KMeans): {silhouette_kmeans}")

# Calculate Davies-Bouldin index for each model
db_index_optics = davies_bouldin_score(X_scaled[columns_to_scale], optics_model.labels_)
db_index_dbscan = davies_bouldin_score(X_scaled[columns_to_scale], dbscan_model.labels_)
db_index_kmeans = davies_bouldin_score(X_scaled[columns_to_scale], kmeans_model.labels_)
print("PREFERE LOW DAVIES-BOULDIN INDEX")
print(f"Davies-Bouldin Index (OPTICS): {db_index_optics}")
print(f"Davies-Bouldin Index (DBSCAN): {db_index_dbscan}")
print(f"Davies-Bouldin Index (KMeans): {db_index_kmeans}")

# Number of outliers in DBSCAN and number of statecharts with max coverage
n_outliers = len(X[(X['dbscan_label'] == -1)])
print(f"\nNumbers of outliers in DBSCAN: {n_outliers}")
n_max_coverage = len(X[(X['SimplifiedSCTUnitCoverage'] == 1.00)])
print(f"Numbers of completely covered statecharts: {n_max_coverage}\n")

# Information about each cluster
print("CLUSTER 0")
cluster_0 = X[(X['dbscan_label'] ==  0)]
print(f"Mean coverage: {statistics.mean(cluster_0['SimplifiedSCTUnitCoverage'])}")
print(f"Median coverage: {statistics.median(cluster_0['SimplifiedSCTUnitCoverage'])}")
print(f"Mean NumStates: {statistics.mean(cluster_0['NumStates'])}")
print(f"Mean AvgDepth: {statistics.mean(cluster_0['AvgDepth'])}")
print(f"Number of statecharts: {len(cluster_0)}")
print(f"Max number of states: {max(cluster_0['NumStates'])}")
print(f"Number of statecharts with 100% coverage: {len(cluster_0[(cluster_0['SimplifiedSCTUnitCoverage'] ==  1.00)])}")
print(f"Number of statecharts with 0% coverage: {len(cluster_0[(cluster_0['SimplifiedSCTUnitCoverage'] ==  0.00)])}")
print(cluster_0)
with open("cluster_0.txt", "w") as f:
  for stc in cluster_0['Statechart']:
    print(stc+"SimplifiedTest,", file=f)

print("CLUSTER 1")
cluster_1 = X[(X['dbscan_label'] ==  1)]
print(f"Mean coverage: {statistics.mean(cluster_1['SimplifiedSCTUnitCoverage'])}")
print(f"Median coverage: {statistics.median(cluster_1['SimplifiedSCTUnitCoverage'])}")
print(f"Mean NumStates: {statistics.mean(cluster_1['NumStates'])}")
print(f"Mean AvgDepth: {statistics.mean(cluster_1['AvgDepth'])}")
print(f"Number of statecharts: {len(cluster_1)}")
print(f"Max number of states: {max(cluster_1['NumStates'])}")
print(f"Number of statecharts with 100% coverage: {len(cluster_1[(cluster_1['SimplifiedSCTUnitCoverage'] ==  1.00)])}")
print(f"Number of statecharts with 0% coverage: {len(cluster_1[(cluster_1['SimplifiedSCTUnitCoverage'] ==  0.00)])}")
print(cluster_1)
with open("cluster_1.txt", "w") as f:
  for stc in cluster_1['Statechart']:
    print(stc+"SimplifiedTest,", file=f)

print("CLUSTER 2")
cluster_2 = X[(X['dbscan_label'] ==  2)]
print(f"Mean coverage: {statistics.mean(cluster_2['SimplifiedSCTUnitCoverage'])}")
print(f"Median coverage: {statistics.median(cluster_2['SimplifiedSCTUnitCoverage'])}")
print(f"Mean NumStates: {statistics.mean(cluster_2['NumStates'])}")
print(f"Mean AvgDepth: {statistics.mean(cluster_2['AvgDepth'])}")
print(f"Number of statecharts: {len(cluster_2)}")
print(f"Max number of states: {max(cluster_2['NumStates'])}")
print(f"Number of statecharts with 0% coverage: {len(cluster_2[(cluster_2['SimplifiedSCTUnitCoverage'] ==  0.00)])}")
print(cluster_2)
with open("cluster_2.txt", "w") as f:
  for stc in cluster_2['Statechart']:
    print(stc+"SimplifiedTest,", file=f)

print("CLUSTER 3")
cluster_3 = X[(X['dbscan_label'] ==  3)]
print(f"Mean coverage: {statistics.mean(cluster_3['SimplifiedSCTUnitCoverage'])}")
print(f"Median coverage: {statistics.median(cluster_3['SimplifiedSCTUnitCoverage'])}")
print(f"Mean NumStates: {statistics.mean(cluster_3['NumStates'])}")
print(f"Mean AvgDepth: {statistics.mean(cluster_3['AvgDepth'])}")
print(f"Number of statecharts: {len(cluster_3)}")
print(f"Max number of states: {max(cluster_3['NumStates'])}")
print(f"Number of statecharts with 0% coverage: {len(cluster_3[(cluster_3['SimplifiedSCTUnitCoverage'] ==  0.00)])}")
print(cluster_3)
with open("cluster_3.txt", "w") as f:
  for stc in cluster_3['Statechart']:
    print(stc+"SimplifiedTest,", file=f)

print("CLUSTER 4")
cluster_4 = X[(X['dbscan_label'] ==  4)]
print(f"Mean coverage: {statistics.mean(cluster_4['SimplifiedSCTUnitCoverage'])}")
print(f"Median coverage: {statistics.median(cluster_4['SimplifiedSCTUnitCoverage'])}")
print(f"Mean NumStates: {statistics.mean(cluster_4['NumStates'])}")
print(f"Mean AvgDepth: {statistics.mean(cluster_4['AvgDepth'])}")
print(f"Number of statecharts: {len(cluster_4)}")
print(f"Max number of states: {max(cluster_4['NumStates'])}")
print(f"Number of statecharts with 0% coverage: {len(cluster_4[(cluster_4['SimplifiedSCTUnitCoverage'] ==  0.00)])}")
print(cluster_4)
with open("cluster_4.txt", "w") as f:
  for stc in cluster_4['Statechart']:
    print(stc+"SimplifiedTest,", file=f)

print("CLUSTER -1")
cluster_m1 = X[(X['dbscan_label'] ==  -1)]
print(f"Mean coverage: {statistics.mean(cluster_m1['SimplifiedSCTUnitCoverage'])}")
print(f"Median coverage: {statistics.median(cluster_m1['SimplifiedSCTUnitCoverage'])}")
print(f"Mean NumStates: {statistics.mean(cluster_m1['NumStates'])}")
print(f"Mean AvgDepth: {statistics.mean(cluster_m1['AvgDepth'])}")
print(f"Number of statecharts: {len(cluster_m1)}")
print(f"Max number of states: {max(cluster_m1['NumStates'])}")
print(f"Number of statecharts with 0% coverage: {len(cluster_m1[(cluster_m1['SimplifiedSCTUnitCoverage'] ==  0.00)])}")
print(cluster_m1)
with open("cluster_m1.txt", "w") as f:
  for stc in cluster_m1['Statechart']:
    print(stc+"SimplifiedTest,", file=f)

# Generate colors for each DBSCAN cluster
unique_labels = np.unique(dbscan_model.labels_)
colors = sns.color_palette('bright', len(unique_labels))

# Plot for DBSCAN in 3D
fig = plt.figure(figsize=(10, 5))
ax = fig.add_subplot(111, projection='3d')
for cluster_label, color in zip(unique_labels, colors):
    cluster_mask = (dbscan_model.labels_ == cluster_label)
    ax.scatter(X_scaled.loc[cluster_mask, 'NumStates'], 
                X_scaled.loc[cluster_mask, 'AvgDepth'], 
                X_scaled.loc[cluster_mask, 'MaxDepth'], 
                label=f'Cluster {cluster_label}', color=color)
ax.set_title('DBSCAN Clustering', fontsize=18)
ax.set_xlabel('NumStates (scaled)', fontsize=14)
ax.set_ylabel('AvgDepth (scaled)', fontsize=14)
ax.set_zlabel('MaxDepth (scaled)', fontsize=14)
ax.legend()

# Plot for KMeans 3D
fig = plt.figure(figsize=(10, 5))
ax = fig.add_subplot(111, projection='3d')
for cluster_label in range(kmeans_model.n_clusters):
    cluster_mask = (kmeans_model.labels_ == cluster_label)
    ax.scatter(X_scaled.loc[cluster_mask, 'NumStates'], 
                X_scaled.loc[cluster_mask, 'AvgDepth'], 
                X_scaled.loc[cluster_mask, 'MaxDepth'], 
                label=f'Cluster {cluster_label}')
ax.scatter(kmeans_model.cluster_centers_[:, 0], 
            kmeans_model.cluster_centers_[:, 1], 
            kmeans_model.cluster_centers_[:, 2], 
            marker='x', color='black', label='Centroids')
ax.set_title('KMeans Clustering')
ax.set_xlabel('NumStates (scaled)')
ax.set_ylabel('AvgDepth (scaled)')
ax.set_zlabel('MaxDepth (scaled)')
ax.legend()

# Plot for DBSCAN in 2D view (dropping AvgDepth)
fig = plt.figure(figsize=(10, 5))
ax = fig.add_subplot(131)
for cluster_label in np.unique(dbscan_model.labels_):
    cluster_mask = (dbscan_model.labels_ == cluster_label)
    ax.scatter(X_scaled.loc[cluster_mask, 'NumStates'], 
                X_scaled.loc[cluster_mask, 'MaxDepth'], 
                label=f'Cluster {cluster_label}')
ax.set_title('DBSCAN Clustering (2D view)', fontsize=16)
ax.set_xlabel('NumStates (scaled)', fontsize=14)
ax.set_ylabel('MaxDepth (scaled)', fontsize=14)
ax.legend()
ax = fig.add_subplot(132)
for cluster_label in np.unique(dbscan_model.labels_):
    cluster_mask = (dbscan_model.labels_ == cluster_label)
    ax.scatter(X_scaled.loc[cluster_mask, 'AvgDepth'], 
                X_scaled.loc[cluster_mask, 'MaxDepth'], 
                label=f'Cluster {cluster_label}')
ax.set_title('DBSCAN Clustering (2D view)', fontsize=16)
ax.set_xlabel('AvgDepth (scaled)', fontsize=14)
ax.set_ylabel('MaxDepth (scaled)', fontsize=14)
ax.legend()
ax = fig.add_subplot(133)
for cluster_label in np.unique(dbscan_model.labels_):
    cluster_mask = (dbscan_model.labels_ == cluster_label)
    ax.scatter(X_scaled.loc[cluster_mask, 'NumStates'], 
                X_scaled.loc[cluster_mask, 'AvgDepth'], 
                label=f'Cluster {cluster_label}')
ax.set_title('DBSCAN Clustering (2D view)', fontsize=16)
ax.set_xlabel('NumStates (scaled)', fontsize=14)
ax.set_ylabel('AvgDepth (scaled)', fontsize=14)
ax.legend()

plt.tight_layout()
plt.show(block=False)

# Coverage Analysis for DBSCAN
# Analyze coverage within DBSCAN clusters
cluster_coverage_stats = X.groupby('dbscan_label')['SimplifiedSCTUnitCoverage'].describe()
# Create a custom color palette for the violin plot with string keys
palette = {str(label): color for label, color in zip(unique_labels, colors)}
# Sort the data by 'dbscan_label' in ascending order
X_sorted = X.sort_values(by='dbscan_label')
# Convert 'dbscan_label' to string type (for the palette)
X['dbscan_label'] = X['dbscan_label'].astype(str)
# Visualize coverage distribution within a violin plot
plt.figure(figsize=(10, 6))
sns.violinplot(x='dbscan_label', y='SimplifiedSCTUnitCoverage', data=X_sorted, palette=palette)
plt.ylim(0, 1)  # Limit y-axis from 0 to 1
plt.title('Coverage Distribution across DBSCAN Clusters', fontsize=30)
plt.xlabel('DBSCAN Cluster Label', fontsize=20)
plt.ylabel('SCTUnit Coverage', fontsize=20)
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.grid(True, axis='y', linestyle='--', alpha=0.7)  # Add grid lines for better readability
# Show plot
plt.tight_layout()
plt.show()