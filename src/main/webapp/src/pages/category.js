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

import CategoryForm from '../components/categoryForm';

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

const Category = () => {
  const [openForm, setOpenForm] = React.useState(false);
  const [editItem, setEditItem] = React.useState({});
  const [categories, setCategories] = React.useState([]);
  const [refresh, setRefresh] = React.useState(0);

  React.useEffect(() => {
    const fetchCategories = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/categories/all`)
        .then(async (res) => {
          let data = await res.json();
          setCategories(data);
        });
    };

    fetchCategories();
  }, [refresh]);

  const handleSubmit = (event) => {
    if (event.id === 0) {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/categories/save`, {
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
      fetch(`http://localhost:8080/api/${process.env.REACT_APP_BACKEND_PORT}/update`, {
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
    fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/categories/delete/${item.id}`, {
        method: 'DELETE'
      }).then(() => {
        setRefresh(Math.random());
      });
  };

  const CategoryActionsCell = (props) => (
    <ActionsCell {...props} enterEdit={enterEdit} deleteItem={deleteItem} />
  );

  return (
    <>
      <AppBar>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
          <button
            className="k-button k-primary"
            onClick={() => enterEdit({ id: 0, name: "" })}
          >
            + Create
          </button>
        </AppBarSection>
      </AppBar>

      <Grid style={{ height: "700px" }} data={categories}>
        <Column field="id" title="Id" width="40px" />
        <Column field="name" title="Name" width="250px" />
        <Column title="Actions" cell={CategoryActionsCell} />
      </Grid>

      {openForm && (
        <CategoryForm
          cancelEdit={handleCancelEdit}
          onSubmit={handleSubmit}
          item={editItem}
        />
      )}
    </>
  );
};

export default Category;
