import React, { useState } from 'react';
import { IonHeader, IonPage, IonTitle, IonToolbar, IonContent, IonItem, IonLabel, IonList, IonButton, IonIcon, IonInput, IonGrid, IonRow, IonCol } from '@ionic/react';
import { add } from 'ionicons/icons';

const StationPage: React.FC = () => {
  const [showAddStationForm, setShowAddStationForm] = useState(false);
  const [stationName, setStationName] = useState('');
  const [stationLines, setStationLines] = useState(1);

  const [stations, setStations] = useState([
    { name: "Item 1", numLines: 5 },
    { name: "Item 2", numLines: 3 },
    { name: "Item 3", numLines: 7 }
  ]);

  const handleAddStation = () => {
    if (stationName.trim() !== '') {
      const newStation = { name: stationName, numLines: stationLines };
      setStations([...stations, newStation]);
      setStationName('');
      setShowAddStationForm(false);
    }
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Station Management</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent color="light">
        <IonList inset={true}>
          <IonItem lines="full">
            <IonLabel style={{ minWidth: '50%' }}>Station Name</IonLabel>
            <IonLabel slot="end">Number of Lines</IonLabel>
          </IonItem>
          {stations.map((item, index) => (
            <IonItem key={index}>
              <IonLabel style={{ minWidth: '50%' }}>{item.name}</IonLabel>
              <IonLabel slot="end">{item.numLines}</IonLabel>
            </IonItem>
          ))}
          {showAddStationForm && (
            <IonItem>
              <IonGrid>
                <IonRow>
                  <IonCol size="6">
                    <IonLabel position="stacked">Enter station name</IonLabel>
                    <IonInput value={stationName} onIonChange={(e) => setStationName(e.detail.value!)}></IonInput>
                  </IonCol>
                  <IonCol size="6">
                    <IonLabel position="stacked">Number of lines</IonLabel>
                    <IonInput type="number" value={stationLines} onIonChange={(e) => setStationLines(parseInt(e.detail.value!, 10))}></IonInput>
                  </IonCol>
                </IonRow>
              </IonGrid>
              <IonButton color="success" slot="end" onClick={handleAddStation}>Save</IonButton>
            </IonItem>
          )}
        </IonList>
        <div style={{ display: 'flex', justifyContent: 'flex-end', paddingRight: '20px' }}>
          {!showAddStationForm && (
            <IonButton onClick={() => setShowAddStationForm(true)}>
              Add new station
              <IonIcon slot="end" icon={add}></IonIcon>
            </IonButton>
          )}
        </div>
      </IonContent>
    </IonPage>
  );
};

export default StationPage;
