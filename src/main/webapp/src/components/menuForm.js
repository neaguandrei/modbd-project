import * as React from "react";
import { Dialog } from "@progress/kendo-react-dialogs";
import { Form, Field, FormElement } from "@progress/kendo-react-form";
import { Input, NumericTextBox } from "@progress/kendo-react-inputs";

const MenuForm = (props) => {
  const title = props.item.id === 0 ? 'Create' : `Edit ${props.item.name}`;

  return (
    <Dialog title={title} onClose={props.cancelEdit}>
      <Form
        onSubmit={props.onSubmit}
        initialValues={props.item}
        render={(formRenderProps) => (
          <FormElement
            style={{
              maxWidth: 650,
            }}
          >
            <fieldset className={"k-form-fieldset"}>
              <div className="mb-3">
                <Field
                  name={"name"}
                  component={Input}
                  label={"Name"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"description"}
                  component={Input}
                  label={"Description"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"unitPrice"}
                  component={NumericTextBox}
                  label={"Unit price"}
                />
              </div>
            </fieldset>
            <div className="k-form-buttons">
              <button
                type={"submit"}
                className="k-button k-primary"
                disabled={!formRenderProps.allowSubmit}
              >
                {props.item.id === 0 ? 'Create' : 'Update'}
              </button>
              <button
                type={"submit"}
                className="k-button"
                onClick={props.cancelEdit}
              >
                Cancel
              </button>
            </div>
          </FormElement>
        )}
      />
    </Dialog>
  );
};

export default MenuForm;
