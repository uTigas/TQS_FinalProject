import React from 'react';
import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonGrid, IonRow, IonCol } from '@ionic/react';
import DashboardCard from '../components/DashboardCard';
import Header from '../components/Header';

const Homepage: React.FC = () => {

  const cardsData = [
    { title: "Stations", subtitle: "Manage stations here", imageSrc: "../../assets/station.jpg"},
    { title: "Trains", subtitle: "Manage trains here", imageSrc: "../../assets/train.jpeg" },
    { title: "Connections", subtitle: "Manage connections here", imageSrc: "../../assets/connection.jpeg" },
  ];

  const renderCards = () => {
    return cardsData.map((data, index) => (
      <IonCol size="12" sizeMd="4" key={index}>
        <DashboardCard data={data} />
      </IonCol>
    ));
  };

  return (
    <IonPage>
      <Header name='Admin Dashboard'/>
      <IonContent fullscreen>
        <IonGrid>
          <IonRow>
            {renderCards()}
          </IonRow>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default Homepage;
