import React, { useState, useEffect } from 'react';
import { IonHeader, IonPage, IonTitle, IonToolbar, IonContent, IonItem, IonLabel, IonList, IonButton, IonIcon, IonInput, IonGrid, IonRow, IonCol, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonCardSubtitle, IonSelect, IonSelectOption } from '@ionic/react';
import APIWrapper from '../components/APIWrapper';
import { TrainType } from '../support/Variables';
import Header from '../components/Header';

interface trainData {
  type: string;
  number: number;
}

const TrainPage: React.FC = () => {
  const [trainNumber, setTrainNumber] = useState<number>(1);
  const [trainType, setTrainType] = useState('');
  const [selectedtrain, setSelectedtrain] = useState<trainData | null>(null);
  const [newErrorMessage, setNewErrorMessage] = useState<string>('');
  const [newSuccessMessage, setNewSuccessMessage] = useState<string>('');

  const [trains, setTrains] = useState<trainData[]>([]);

  useEffect(() => {
    APIWrapper.fetchTrainList()
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .then(trainData => {
        setTrains(trainData);
      })
      .catch(error => {
        console.error('Error:', error);
      })
  }, []);

  const handleAddTrain = () => {
    const newTrain: trainData = { type: trainType, number: trainNumber };
    const validtrain = checkValidtrain(newTrain);
    if (validtrain !== true) {
      setNewSuccessMessage('');
      setNewErrorMessage(validtrain as string);
      return;
    }

    // API call to add train
    APIWrapper.addTrain(trainType, trainNumber)
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .then(trainData => {
        setTrains([...trains, newTrain]);
        setTrainNumber(-1);
        setTrainType('');
        setNewErrorMessage('');
        setNewSuccessMessage('Train created successfully.');
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  const checkValidtrain = (train: trainData) => {
    if (!(Object.keys(TrainType).slice(5).includes(train.type))) {
      return "Invalid Train Class.";
    }

    if (train.number < 1 || train.number > 9999 || isNaN(train.number)) {
      return "Train Number must be between 1 and 9999.";
    }

    return true;
  }

  const handleNewtrainNumberChange = (e: CustomEvent) => {
    e.preventDefault();
    setTrainNumber(e.detail.value);
  }

  const handleClear = () => {
    setTrainNumber(-1);
    setTrainType('');
  };

  const handleSelecttrain = (train: trainData) => {
    setSelectedtrain(train);
  };

  return (
    <IonPage>
      <Header name={'Train Management'} />
      <IonContent color="light">
        <IonGrid>
          <IonRow>
            <IonCol size="8">
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Train List</IonCardTitle>
                  <IonCardSubtitle>Click on a train to view more information and edit it</IonCardSubtitle>
                </IonCardHeader>
                <IonList inset={true}>
                  <IonItem lines="full">
                    <IonLabel style={{ minWidth: '50%' }}>Name</IonLabel>
                  </IonItem>
                  {trains.map((item, index) => (
                    <IonItem key={index} onClick={() => handleSelecttrain(item)}>
                      <IonLabel style={{ minWidth: '50%' }}>{item.type}-{item.number}</IonLabel>
                    </IonItem>
                  ))}
                </IonList>
              </IonCard>
            </IonCol>

            <IonCol size="4">
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Train Information</IonCardTitle>
                </IonCardHeader>
                <IonCardContent>
                  {selectedtrain ? (
                    <>
                      <p><strong>Class:</strong> {selectedtrain.type}</p>
                      <p><strong>Number:</strong> {selectedtrain.number}</p>
                      <IonButton expand="full" fill="clear" onClick={() => console.log('Edit button clicked')}>Edit</IonButton>
                    </>
                  ) : (
                    <p>No train selected.</p>
                  )}
                </IonCardContent>
              </IonCard>
              {/* Card para adicionar nova estação */}
              <IonCard>
                <IonCardHeader>
                  <IonCardTitle>Add New train</IonCardTitle>
                </IonCardHeader>
                <IonCardContent style={{ gap: '20px' }}>
                <IonLabel position="stacked">Select train Class</IonLabel>
                  <IonSelect name="newTrainClass" value={trainType}  onIonChange={(e) => setTrainType(e.detail.value)}>
                    <IonSelectOption value="ALPHA">ALPHA</IonSelectOption> 
                    <IonSelectOption value="INTER">INTER</IonSelectOption> 
                    <IonSelectOption value="REGIONAL">REGIONAL</IonSelectOption> 
                    <IonSelectOption value="SPECIAL">SPECIAL</IonSelectOption> 
                    <IonSelectOption value="URBAN">URBAN</IonSelectOption> 
                  </IonSelect>
                  <IonInput 
                  placeholder='Enter train Number'
                    name="newTrainNumber"
                    onIonChange={handleNewtrainNumberChange}
                  ></IonInput>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <IonButton color="danger" onClick={handleClear}>Clear</IonButton>
                    <IonButton color="success" slot="end" onClick={handleAddTrain}>Add</IonButton>
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

export default TrainPage;
