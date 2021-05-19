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

import MenuForm from '../components/menuForm';

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

const Menu = () => {
  const [openForm, setOpenForm] = React.useState(false);
  const [editItem, setEditItem] = React.useState({});
  const [menus, setMenus] = React.useState([]);
  const [refresh, setRefresh] = React.useState(0);

  React.useEffect(() => {
    const fetchMenus = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/menus/all`)
        .then(async (res) => {
          let data = await res.json();
          setMenus(data);
        });
    };

    fetchMenus();
  }, [refresh]);

  const handleSubmit = (event) => {
    if (event.id === 0) {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/menus/save`, {
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
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/menus/update`, {
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
    fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/menus/delete/${item.id}`, {
        method: 'DELETE'
      }).then(() => {
        setRefresh(Math.random());
      });
  };

  const MenuActionsCell = (props) => (
    <ActionsCell {...props} enterEdit={enterEdit} deleteItem={deleteItem} />
  );

  return (
    <>
      <AppBar>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
          <button
            className="k-button k-primary"
            onClick={() => enterEdit({ id: 0, name: "", description: "", unitPrice: 0.0 })}
          >
            + Create
          </button>
        </AppBarSection>
      </AppBar>

      <Grid style={{ height: "700px" }} data={menus}>
        <Column field="id" title="Id" width="150px" />
        <Column field="name" title="Name" />
        <Column field="description" title="Description" />
        <Column field="unitPrice" title="Unit price" />
        <Column title="Actions" cell={MenuActionsCell} />
      </Grid>

      {openForm && (
        <MenuForm
          cancelEdit={handleCancelEdit}
          onSubmit={handleSubmit}
          item={editItem}
        />
      )}
    </>
  );
};

export default Menu;
