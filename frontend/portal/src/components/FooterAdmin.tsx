// FooterAdmin.tsx

import React from 'react';
import { IonTabButton, IonIcon, IonLabel } from '@ionic/react';
import { triangle, ellipse, square } from 'ionicons/icons';

const FooterAdmin: React.FC = () => {
  return (
      
     <><IonTabButton tab="dashboard" href="/dashboard">
      <IonIcon aria-hidden="true" icon={triangle} />
      <IonLabel>Dashboard</IonLabel>
    </IonTabButton><IonTabButton tab="stations" href="/stations">
        <IonIcon aria-hidden="true" icon={ellipse} />
        <IonLabel>Stations</IonLabel>
      </IonTabButton><IonTabButton tab="trains" href="/trains">
        <IonIcon aria-hidden="true" icon={square} />
        <IonLabel>Trains</IonLabel>
      </IonTabButton></>

  );
};

export default FooterAdmin;
