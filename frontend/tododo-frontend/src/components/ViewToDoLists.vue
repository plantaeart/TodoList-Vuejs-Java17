<script setup lang="ts">
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { onMounted, ref } from 'vue'
import DisplayToDoListItem from './list/DisplayToDoListItem.vue'
import AddToDoList from './list/AddToDoList.vue'

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
    <div>
      <p class="flex justify-center mb-1" v-if="isLoading">Loading...</p>
      <p class="flex justify-center mb-1" v-else>
        {{ 'Count the number of lists : ' + store.sizeToDoList }}
      </p>
    </div>
    <div>
      <AddToDoList />
    </div>
    <div
      class="flex flex-row items-center"
      v-for="(toDoList, index) in store.toDoListResp.toDoLists"
      :key="index"
    >
      <DisplayToDoListItem :to-do-list="toDoList" />
    </div>
  </div>
</template>

<style scoped></style>
