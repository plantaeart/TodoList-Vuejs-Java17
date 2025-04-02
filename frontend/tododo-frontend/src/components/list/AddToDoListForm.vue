<script setup lang="ts">
import { ref } from 'vue'
import InputText from 'primevue/inputtext'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const toDoListName = ref('')

const addToDoListFromFormInfos = async () => {
  console.log('Adding todo list')
  req.toDoLists = [{ name: toDoListName.value }]
  await store.addToDoList(req)
}

defineExpose({
  addToDoListFromFormInfos,
})
</script>

<template>
  <div class="flex flex-col items-center m-4">
    <div class="flex flex-col gap-2">
      <label for="toDoListName">Todo list name</label>
      <InputText id="toDoListName" v-model="toDoListName" aria-describedby="todo list name" />
    </div>
  </div>
</template>

<style scoped></style>
