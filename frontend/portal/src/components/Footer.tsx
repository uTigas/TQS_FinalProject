// Footer.tsx

import React from 'react';
import { IonTabBar, IonTabButton, IonIcon, IonLabel } from '@ionic/react';
import { triangle, ellipse, square } from 'ionicons/icons';

const Footer: React.FC = () => {
  return (

      <><IonTabButton tab="tab1" href="/tab1">
      <IonIcon aria-hidden="true" icon={triangle} />
      <IonLabel>Tab 1</IonLabel>
    </IonTabButton><IonTabButton tab="tab2" href="/tab2">
        <IonIcon aria-hidden="true" icon={ellipse} />
        <IonLabel>Tab 2</IonLabel>
      </IonTabButton><IonTabButton tab="tab3" href="/tab3">
        <IonIcon aria-hidden="true" icon={square} />
        <IonLabel>Tab 3</IonLabel>
      </IonTabButton></>

  );
};

export default Footer;
