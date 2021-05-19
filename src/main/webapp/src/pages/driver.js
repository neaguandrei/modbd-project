import React from 'react';

import {
  Grid,
  GridColumn as Column,
} from "@progress/kendo-react-grid";

import {
  AppBar,
  AppBarSection,
  AppBarSpacer
} from "@progress/kendo-react-layout";

import DriverForm from '../components/driverForm';

const ActionsCell = (props) => {
  return (
    <td>
      <button
        className="k-button k-primary"
        onClick={() => props.enterEdit(props.dataItem)}
      >
        Edit
      </button>
      &nbsp;
      <button
        className="k-button k-primary"
        onClick={() => props.deleteItem(props.dataItem)}
      >
        Delete
      </button>
    </td>
  );
};

const Driver = () => {
  const [openForm, setOpenForm] = React.useState(false);
  const [editItem, setEditItem] = React.useState({});
  const [drivers, setDrivers] = React.useState([]);
  const [refresh, setRefresh] = React.useState(0);

  React.useEffect(() => {
    const fetchDrivers = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/drivers/all`)
        .then(async (res) => {
          let data = await res.json();
          setDrivers(data);
        });
    };

    fetchDrivers();
  }, [refresh]);

  const handleSubmit = (event) => {
    if (event.id === 0) {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/drivers/save`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(event)
      }).then(() => {
        setOpenForm(false);
        setRefresh(Math.random());
      });
    } else {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/drivers/update`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(event)
      }).then(() => {
        setOpenForm(false);
        setRefresh(Math.random());
      });
    }
  };

  const handleCancelEdit = () => {
    setOpenForm(false);
  };

  const enterEdit = (item) => {
    setOpenForm(true);
    setEditItem(item);
  };

  const deleteItem = (item) => {
    fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/drivers/delete/${item.id}`, {
        method: 'DELETE'
      }).then(() => {
        setRefresh(Math.random());
      });
  };

  const DriverActionsCell = (props) => (
    <ActionsCell {...props} enterEdit={enterEdit} deleteItem={deleteItem} />
  );

  return (
    <>
      <AppBar>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
          <button
            className="k-button k-primary"
            onClick={() => enterEdit({ id: 0, name: "", user: { email: "", password: "" }})}
          >
            + Create
          </button>
        </AppBarSection>
      </AppBar>

      <Grid style={{ height: "700px" }} data={drivers}>
        <Column field="id" title="Id" width="150px" />
        <Column field="name" title="Name" width="300px" />
        <Column field="user.id" title="Id User" width="150px" />
        <Column field="user.email" title="Email" width="300px" />
        <Column field="user.password" title="Password" width="200px" />
        <Column title="Actions" cell={DriverActionsCell} />
      </Grid>

      {openForm && (
        <DriverForm
          cancelEdit={handleCancelEdit}
          onSubmit={handleSubmit}
          item={editItem}
        />
      )}
    </>
  );
};

export default Driver;
