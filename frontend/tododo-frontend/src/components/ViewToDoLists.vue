<script setup lang="ts">
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ref, watch } from 'vue'
import DisplayToDoListItem from './toDoList/DisplayToDoListItem.vue'
import AddToDoList from './toDoList/addToDoList/AddToDoList.vue'
import DisplayTaskItem from './task/DisplayTaskItem.vue'
import DisplaySubTaskItem from './subTask/DisplaySubTaskItem.vue'
import type { ToDoList } from '@/features/toDoList/ToDoList'
import appEnv from 'app-env'

const store = useToDoListStore()
const localToDoLists = ref<ToDoList[]>([]) // Local state to hold fetched data
const debug = ref(appEnv.VITE_DEBUG)

const props = defineProps({
  toDoLists: {
    type: Object as () => Array<ToDoList>, // Specify the type of the prop
    required: true, // Make it required
  },
  isLoading: {
    type: Boolean, // Specify the type of the prop
    required: true, // Make it required
  },
})

localToDoLists.value = props.toDoLists // Initialize local state with the prop value

// Watch for changes in the prop and update the local copy
watch(
  () => props.toDoLists,
  (newToDoLists) => {
    localToDoLists.value = newToDoLists // Update the local copy when the prop changes
  },
  { deep: true },
)
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
    <div class="grid grid-cols-2 m-4 gap-4">
      <div v-for="toDoList in localToDoLists" :key="toDoList.id">
        <div :class="[toDoList.color?.color]" class="rounded-lg w-full shadow-md p-4">
          <p v-if="debug">Todo list name : {{ toDoList.name }} and id : {{ toDoList.id }}</p>
          <DisplayToDoListItem :to-do-list="toDoList" />
          <div class="flex flex-col ml-6" v-for="task in toDoList.tasks" :key="task.id">
            <DisplayTaskItem :task="task" :id-list="toDoList.id as number" />
            <div
              class="flex flex-row items-center ml-10"
              v-for="subTask in task.subTasks"
              :key="subTask.id"
            >
              <DisplaySubTaskItem
                :sub-task="subTask"
                :id-list="toDoList.id as number"
                :id-task="task.id as number"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
