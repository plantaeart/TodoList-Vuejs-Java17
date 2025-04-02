<script setup lang="ts">
import Checkbox from 'primevue/checkbox'
import type { ToDoList } from '@/features/toDoList/ToDoList'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import type { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { ref } from 'vue'
import dayjs from 'dayjs'

const store = useToDoListStore()

// Define props
const props = defineProps({
  toDoList: {
    type: Object as () => ToDoList, // Specify the type of the prop
    required: true, // Make it required
  },
})

// Create a local reactive copy of the prop
const localToDoList = ref(props.toDoList)

// Emit events to the parent
const emit = defineEmits(['update:isCompleted'])

// Handle checkbox change
const onCheckboxChange = async (value: boolean) => {
  // Update the local copy
  localToDoList.value.isCompleted = value
  // Emit the updated value to the parent
  emit('update:isCompleted', value)
  localToDoList.value.updateDate = dayjs().format('DD/MM/YYYY HH:mm:ss')
  const req: ToDoListRequest = {
    idsList: [localToDoList.value.id as number],
    toDoLists: [{ ...localToDoList.value }], // Use the local copy
    isTest: false,
  }
  console.log('Start updating List id : ', localToDoList.value.id)
  await store.updateToDoList(req)
  console.log('Response from updateToDoList:', store.toDoListResp)
}
</script>

<template>
  <div class="m-4">
    <div class="flex flex-row items-center">
      <p class="mr-4">{{ localToDoList.name }}</p>
      <Checkbox
        :model-value="localToDoList.isCompleted"
        @update:model-value="onCheckboxChange"
        binary
      />
    </div>
  </div>
</template>

<style scoped></style>
