<script setup lang="ts">
import Button from 'primevue/button'
import { ref } from 'vue'
import AddToDoListForm from './AddToDoListForm.vue'

const isCreatingToDoList = ref(false)
const isCreateButtonDisabled = ref(false)
const AddToDoListFormRef = ref()

const clickAddToDoList = () => {
  isCreatingToDoList.value = !isCreatingToDoList.value
  isCreateButtonDisabled.value = !isCreateButtonDisabled.value

  if (isCreatingToDoList.value) {
    // Reset the form when opening
    AddToDoListFormRef.value?.addInitForm()
  }
}

const clickAddToDoListFromFormInfos = () => {
  // Call the exposed method in the child component
  AddToDoListFormRef.value?.addToDoListFromFormInfos()
  clickAddToDoList()
}
</script>

<template>
  <div class="flex flex-col items-center m-4">
    <Button
      class="w-1/4 mb-4"
      label="Add todo list"
      icon="pi pi-plus"
      @click="clickAddToDoList"
      raised
      :disabled="isCreateButtonDisabled"
    />
    <div class="flex flex-col items-center justify-center w-4/6" v-if="isCreatingToDoList">
      <AddToDoListForm ref="AddToDoListFormRef" />
      <div class="flex flex-row justify-center">
        <Button
          class="w-full m-4"
          severity="danger"
          label="Cancel"
          icon="pi pi-times"
          raised
          @click="clickAddToDoList"
        />
        <Button
          class="w-full m-4"
          label="Create"
          icon="pi pi-check"
          raised
          @click="clickAddToDoListFromFormInfos"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
