import { IonContent, IonHeader, IonItem, IonLabel, IonList, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab3.css';
import Header from '../components/Header';
import APIWrapper from '../components/APIWrapper';
import { useEffect, useState } from 'react';
import { Ticket } from '../support/Variables';

const Tab3: React.FC = () => {
  const [tickets, setTickets] = useState<Ticket[]>([]);

  const fetchTickets = () => {
    APIWrapper.fetchTickets()
    .then((response: Response | undefined) => {
      if (response && response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to fetch data');
      }
    })
    .then(stationData => {
      console.log(stationData)
      setTickets(stationData);
    })
    .catch(error => {
      console.error('Error:', error);
    })
  }

  useEffect(()=>{
    fetchTickets()
  }, [])

  return (
    <IonPage>
      <IonHeader>
        <Header name={'Tickets'} />
      </IonHeader>
      <IonContent fullscreen className='ion-padding'>
            <IonTitle size="large">Your tickets:</IonTitle>
            <IonList inset={true}>
              {tickets.map((item,index) => (
                <IonItem key={index}>
                  <IonLabel>{item.id}</IonLabel>
                  <IonLabel>{item.origin}</IonLabel>
                  <IonLabel>{item.to}</IonLabel>
                  <IonLabel>{item.totalPrice}€</IonLabel>
                  <IonLabel>{item.departure}</IonLabel>
                  <IonLabel>{item.arrival}</IonLabel>
                </IonItem>
              ))}
            </IonList>
      </IonContent>
    </IonPage>
  );
};

export default Tab3;
