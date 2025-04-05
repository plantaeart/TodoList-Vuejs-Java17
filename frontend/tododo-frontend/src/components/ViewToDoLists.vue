<script setup lang="ts">
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { onMounted, ref } from 'vue'
import DisplayToDoListItem from './toDoList/DisplayToDoListItem.vue'
import AddToDoList from './toDoList/addToDoList/AddToDoList.vue'
import DisplayTaskItem from './task/DisplayTaskItem.vue'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const isLoading = ref(true) // Track loading state

onMounted(async () => {
  await store.getAllToDoLists(req) // Wait for the API call to complete
  isLoading.value = false // Set loading to false after data is fetched
})
</script>

<template>
  <div class="m-4">
    <div class="flex flex-col items-center mb-1">
      <h1 class="text-xl">Tododo</h1>
      <h2 class="text-l underline">To do and do !</h2>
      <p v-if="isLoading">Loading...</p>
      <p v-else>
        {{ 'Count the number of lists : ' + store.sizeToDoList }}
      </p>
    </div>
    <div>
      <AddToDoList />
    </div>
    <div
      class="flex flex-col items-start"
      v-for="toDoList in store.allToDoListState"
      :key="toDoList.id"
    >
      <DisplayToDoListItem :to-do-list="toDoList" />
      <div class="flex flex-row items-center ml-10" v-for="task in toDoList.tasks" :key="task.id">
        <DisplayTaskItem :task="task" :id-list="toDoList.id as number" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
