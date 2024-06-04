import React, { useState, useEffect } from 'react';
import { IonHeader, IonPage, IonTitle, IonToolbar, IonContent, IonItem, IonLabel, IonList, IonButton, IonIcon, IonInput, IonGrid, IonRow, IonCol, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonCardSubtitle, IonModal } from '@ionic/react';
import { add, pencil, close } from 'ionicons/icons';
import APIWrapper from '../components/APIWrapper';

interface StationData {
  name: string;
  numberOfLines: number;
}

const StationPage: React.FC = () => {
  //All data
  const [stations, setStations] = useState<StationData[]>([]);
  //New station data
  const [stationName, setStationName] = useState('');
  const [stationLines, setStationLines] = useState(1);
  const [newErrorMessage, setNewErrorMessage] = useState<string>('');
  const [newSuccessMessage, setNewSuccessMessage] = useState<string>('');
  //Edit station data
  const [selectedStation, setSelectedStation] = useState<StationData | null>(null);
  const [editStationName, setEditStationName] = useState('');
  const [editStationLines, setEditStationLines] = useState(1);
  const [editErrorMessage, setEditErrorMessage] = useState<string>('');
  const [editSuccessMessage, setEditSuccessMessage] = useState<string>('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

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
        setStations(stationData);
        console.log(stationData);
      })
      .catch(error => {
        console.error('Error:', error);
      })
  }, []);

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

  // Add Station Functions
  const handleAddStation = () => {
    setNewErrorMessage('');
    setNewSuccessMessage('');
    const newStation: StationData = { name: stationName, numberOfLines: stationLines };
    const validStation = checkValidStation(newStation);
    if (validStation !== true) {
      setNewSuccessMessage('');
      setNewErrorMessage(validStation as string);
      return;
    }

    APIWrapper.addStation(stationName, stationLines)
      .then((stationData) => {
        console.log(stationData);
        setStations([...stations, newStation]);
        setStationName('');
        setStationLines(1);
        setNewErrorMessage('');
        setNewSuccessMessage('Station created successfully.');
        setSelectedStation(newStation);
      })
      .catch(error => {
        console.error('Error:', error);
        setNewErrorMessage(`${error.message}`);
      });
  };

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


  // Edit Station Functions
  const handleSelectStation = (station: StationData) => {
    setSelectedStation(station);
    setEditStationName(station.name);
    setEditStationLines(station.numberOfLines);
  };

  const handleEditStationNameChange = (e: CustomEvent) => {
    e.preventDefault();
    setEditStationName(e.detail.value);
  }

  const handleEditStationLinesChange = (e: CustomEvent) => {
    e.preventDefault();
    //if the input is not a number dont change the value
    if (isNaN(parseInt(e.detail.value, 10))) {
      return;
    }
    setEditStationLines(parseInt(e.detail.value, 10));
  };

  const handleEditStation = () => {
    if (!selectedStation) return;

    const updatedStation: StationData = { name: editStationName, numberOfLines: editStationLines };
    const validStation = checkValidStation(updatedStation);
    if (validStation !== true) {
      setEditSuccessMessage('');
      setEditErrorMessage(validStation as string);
      return;
    }

    APIWrapper.editStation(selectedStation.name, editStationName, editStationLines)
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to edit station');
        }
      })
      .then(stationData => {
        setStations(stations.map(station =>
          station.name === selectedStation.name ? updatedStation : station
        ));
        const updatedStations = stations.map(station =>
          station.name === selectedStation.name ? updatedStation : station
        );
        setStations(updatedStations);
        setSelectedStation(updatedStation);
        setEditErrorMessage('');
        setEditSuccessMessage('Station edited successfully.');
      })
      .catch(error => {
        console.error('Error:', error);
        setEditErrorMessage('An error ocurred while editing the station');
      });
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Station Management</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent color="light">
        <IonGrid>
          <IonRow>
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
            <IonCol size="4">
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Station Information</IonCardTitle>
                </IonCardHeader>
                <IonCardContent>
                  {selectedStation ? (
                    <>
                      <p><strong>Name:</strong> {selectedStation.name}</p>
                      <p><strong>Number of Lines:</strong> {selectedStation.numberOfLines}</p>
                      <IonButton expand="full" fill="clear" onClick={openModal}>Edit</IonButton>
                    </>
                  ) : (
                    <p>No station selected.</p>
                  )}
                </IonCardContent>
              </IonCard>
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
                    value={stationLines}
                    onIonChange={handleNewStationLinesChange}
                  ></IonInput>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <IonButton color="danger" onClick={handleClear}>Clear</IonButton>
                    <IonButton color="success" slot="end" onClick={handleAddStation}>Add</IonButton>
                  </div>
                  {newErrorMessage && <p style={{ color: 'red' }}>{newErrorMessage}</p>}
                  {newSuccessMessage && <p id="successMessage" style={{ color: 'green' }}>{newSuccessMessage}</p>}
                </IonCardContent>
              </IonCard>
            </IonCol>
          </IonRow>
        </IonGrid>
      </IonContent>
      <IonModal isOpen={isModalOpen} onDidDismiss={closeModal}>
        <IonHeader>
          <IonToolbar>
            <IonTitle>Edit Station</IonTitle>
            <IonButton slot="end" onClick={closeModal}>
              <IonIcon icon={close}></IonIcon>
            </IonButton>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <IonCard>
            <IonCardHeader>
              <IonCardTitle>Currently Editing Station [{selectedStation?.name}]</IonCardTitle>
            </IonCardHeader>
            <IonCardContent>
              <IonLabel position="stacked">Edit station name</IonLabel>
              <IonInput
                name="editStationName"
                value={editStationName}
                onIonChange={handleEditStationNameChange}
              ></IonInput>
              <IonLabel position="stacked">Number of lines</IonLabel>
              <IonInput
                name="editStationLines"
                type="number"
                value={editStationLines}
                onIonChange={handleEditStationLinesChange}
              ></IonInput>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <IonButton color="danger" onClick={closeModal}>Cancel</IonButton>
                <IonButton color="success" slot="end" onClick={handleEditStation}>Save Changes</IonButton>
              </div>
              {editErrorMessage && <p style={{ color: 'red' }}>{editErrorMessage}</p>}
              {editSuccessMessage && <p style={{ color: 'green' }}>{editSuccessMessage}</p>}
            </IonCardContent>
          </IonCard>
        </IonContent>
      </IonModal>
    </IonPage>
  );
};

export default StationPage;
