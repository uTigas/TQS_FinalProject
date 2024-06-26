import React, { useState, useEffect } from 'react';
import { IonHeader, IonPage, IonTitle, IonToolbar, IonContent, IonItem, IonLabel, IonList, IonButton, IonIcon, IonInput, IonGrid, IonRow, IonCol, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonCardSubtitle } from '@ionic/react';
import APIWrapper from '../components/APIWrapper';
import { StationData } from '../support/Variables';
import Header from '../components/Header';

const StationPage: React.FC = () => {
  const [stationName, setStationName] = useState('');
  const [stationLines, setStationLines] = useState(1);
  const [selectedStation, setSelectedStation] = useState<StationData | null>(null);
  const [newErrorMessage, setNewErrorMessage] = useState<string>('');
  const [newSuccessMessage, setNewSuccessMessage] = useState<string>('');

  const [stations, setStations] = useState<StationData[]>([]);

  useEffect(() => {
    APIWrapper.fetchStationList()
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .then(stationData => {
        console.log(stationData)
        setStations(stationData);
      })
      .catch(error => {
        console.error('Error:', error);
      })
  }, []);

  const handleAddStation = () => {
    const newStation: StationData = {name: stationName, numberOfLines: stationLines };
    const validStation = checkValidStation(newStation);
    if (validStation !== true) {
      setNewSuccessMessage('');
      setNewErrorMessage(validStation as string);
      return;
    }

    // API call to add station
    APIWrapper.addStation(stationName, stationLines)
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .then(stationData => {
        setStations([...stations, newStation]);
        setStationName('');
        setStationLines(1);
        setNewErrorMessage('');
        setNewSuccessMessage('Station created successfully.');
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  const checkValidStation = (station: StationData) => {
    if (station.name.trim() === '') {
      return "Station name cannot be empty.";
    }

    if (station.name.length < 3 || station.name.length > 255) {
      return "Station name must be between 3 and 255 characters.";
    }

    if (station.numberOfLines < 1 || station.numberOfLines > 30) {
      return "Number of lines must be between 1 and 30.";
    }

    return true;
  }

  const handleNewStationNameChange = (e: CustomEvent) => {
    e.preventDefault();
    setStationName(e.detail.value);
  }

  const handleNewStationLinesChange = (e: CustomEvent) => {
    e.preventDefault();
    //if the input is not a number dont change the value
    if (isNaN(parseInt(e.detail.value, 10))) {
      return;
    }
    setStationLines(parseInt(e.detail.value, 10));
  }

  const handleClear = () => {
    setStationName('');
    setStationLines(1);
  };

  const handleSelectStation = (station: StationData) => {
    setSelectedStation(station);
  };

  return (
    <IonPage>
      <Header name={'Station Management'} />
      <IonContent color="light">
        <IonGrid>
          <IonRow>
            {/* Coluna 1: Lista de estações */}
            <IonCol size="8">
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Station List</IonCardTitle>
                  <IonCardSubtitle>Click on a station to view more information and edit it</IonCardSubtitle>
                </IonCardHeader>
                <IonList inset={true}>
                  <IonItem lines="full">
                    <IonLabel style={{ minWidth: '50%' }}>Station Name</IonLabel>
                    <IonLabel slot="end">Number of Lines</IonLabel>
                  </IonItem>
                  {stations.map((item, index) => (
                    <IonItem key={index} onClick={() => handleSelectStation(item)}>
                      <IonLabel style={{ minWidth: '50%' }}>{item.name}</IonLabel>
                      <IonLabel slot="end">{item.numberOfLines}</IonLabel>
                    </IonItem>
                  ))}
                </IonList>
              </IonCard>
            </IonCol>

            {/* Coluna 2: Formulários para adicionar/editar uma nova estação */}
            <IonCol size="4">
              {/* Card para exibir informações da estação selecionada */}
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Station Information</IonCardTitle>
                </IonCardHeader>
                <IonCardContent>
                  {selectedStation ? (
                    <>
                      <p><strong>Name:</strong> {selectedStation.name}</p>
                      <p><strong>Number of Lines:</strong> {selectedStation.numberOfLines}</p>
                      <IonButton expand="full" fill="clear" onClick={() => console.log('Edit button clicked')}>Edit</IonButton>
                    </>
                  ) : (
                    <p>No station selected.</p>
                  )}
                </IonCardContent>
              </IonCard>
              {/* Card para adicionar nova estação */}
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Add New Station</IonCardTitle>
                </IonCardHeader>
                <IonCardContent style={{ gap: '20px' }}>
                  <IonLabel position="stacked">Enter station name</IonLabel>
                  <IonInput
                    name="newStationName"
                    value={stationName}
                    onIonChange={handleNewStationNameChange}
                  ></IonInput>
                  <IonLabel position="stacked">Number of lines</IonLabel>
                  <IonInput
                    name="newStationLines"
                    type="number"
                    onIonChange={handleNewStationLinesChange}
                  ></IonInput>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <IonButton color="danger" onClick={handleClear}>Clear</IonButton>
                    <IonButton color="success" slot="end" onClick={handleAddStation}>Add</IonButton>
                  </div>
                  {newErrorMessage && <p style={{ color: 'red' }}>{newErrorMessage}</p>}
                  {newSuccessMessage && <p style={{ color: 'green' }}>{newSuccessMessage}</p>}
                </IonCardContent>
              </IonCard>
            </IonCol>
          </IonRow>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default StationPage;
