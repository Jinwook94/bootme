import * as React from "react";
import {Admin, fetchUtils, Resource} from 'react-admin';
import jsonServerProvider from 'ra-data-json-server';
import {UserList} from "./components/users";
import authProvider from "./auth/authProvider";

const dataProvider = jsonServerProvider('https://jsonplaceholder.typicode.com')

const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider}>
        <Resource name="users" list={UserList} />
    </Admin>
    );

export default App;