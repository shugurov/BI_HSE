package ru.hse.shugurov.bi_application;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import java.util.Arrays;

import ru.hse.shugurov.bi_application.gui.NavigationDrawerFragment;
import ru.hse.shugurov.bi_application.gui.adapters.NavigationDrawerAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BackStack;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.AboutAppFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.AboutUsFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.EventsFragment;
import ru.hse.shugurov.bi_application.gui.fragments.lists.FragmentWithList;
import ru.hse.shugurov.bi_application.gui.fragments.special.BillboardFragment;
import ru.hse.shugurov.bi_application.gui.fragments.special.ScheduleFragment;
import ru.hse.shugurov.bi_application.gui.fragments.special.SettingFragment;
import ru.hse.shugurov.bi_application.sections.EventsScreen;
import ru.hse.shugurov.bi_application.sections.MultipleAdaptersViewSection;
import ru.hse.shugurov.bi_application.sections.ReferencesSection;
import ru.hse.shugurov.bi_application.sections.Section;
import ru.hse.shugurov.bi_application.sections.SingleViewSection;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks//TODO объйвления не работают
{
    private static final String BACK_STACKS_TAG = "back stacks";
    private static final String SELECTED_ITEM_TAG = "selected item";
    //TODO а правильные ли вещи в рейтинге показываются?
    //TODO неправильно сохраняется номер выбранного элемента
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private String mTitle;
    private Section[] sections;
    private BaseFragment[] fragments;
    private BaseFragment current;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private int selectedIndex; //TODO не рабоатет
    private boolean isRestored;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isRestored = savedInstanceState != null;
        ImageLoader.init(this);
        FileManager.init(this);
        ApplicationStructure.setContext(this);
        ApplicationStructure structure = ApplicationStructure.getStructure();
        sections = structure.getSections();
        if (savedInstanceState == null)
        {
            fragments = new BaseFragment[sections.length];
            for (int i = 0; i < fragments.length; i++)
            {
                fragments[i] = null;
            }
        } else
        {
            selectedIndex = savedInstanceState.getInt(SELECTED_ITEM_TAG);
            Parcelable[] array = savedInstanceState.getParcelableArray(BACK_STACKS_TAG);
            BackStack[] stacks = Arrays.copyOf(array, array.length, BackStack[].class);
            fragments = new BaseFragment[stacks.length];
            for (int i = 0; i < stacks.length; i++)
            {
                BackStack currentStack = stacks[i];
                if (currentStack == null)
                {
                    fragments[i] = null;
                } else
                {
                    currentStack.restoreBackStack(this);
                    fragments[i] = currentStack.getMainFragment();
                }
            }
            current = fragments[selectedIndex];
        }
        navigationDrawerAdapter = new NavigationDrawerAdapter(this, sections);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = sections[0].getTitle();

        // Set up the drawer.

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), navigationDrawerAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        BackStack[] stacks = new BackStack[fragments.length];
        for (int i = 0; i < fragments.length; i++)
        {
            if (fragments[i] == null)
            {
                stacks[i] = null;
            } else
            {
                stacks[i] = fragments[i].getBackStack();
            }
        }
        outState.putParcelableArray(BACK_STACKS_TAG, stacks);
        outState.putInt(SELECTED_ITEM_TAG, selectedIndex);
        Log.d("my log", "Selected item: " + selectedIndex);
    }

    @Override
    public void onBackPressed()
    {
        Fragment fragmentToBeShown = current.popPreviousFragment();
        if (fragmentToBeShown == null)
        {
            super.onBackPressed();
        } else
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragmentToBeShown);
            transaction.commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(final int position)
    {
        if (navigationDrawerAdapter != null)
        {
            if (isRestored)
            {
                navigationDrawerAdapter.setPosition(selectedIndex);
            } else
            {
                selectedIndex = position;
                navigationDrawerAdapter.setPosition(position);
            }
        }
        if (isRestored)
        {
            isRestored = false;
            return;
        }
        selectedIndex = position;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragments[position] == null)
        {
            createNewFragment(position);
            fragments[position] = current;
        } else
        {
            current = fragments[position].popCurrentFragment();
            if (current == null)
            {
                current = fragments[position];
            }
        }
        fragmentManager.beginTransaction().replace(R.id.container, current).commit();
    }

    private void createNewFragment(int position)
    {
        Bundle arguments = new Bundle();
        arguments.putSerializable(FragmentWithList.SECTION_TAG, sections[position]);
        switch (sections[position].getType())
        {
            case ContentTypes.NEWS:
                if (sections[position] instanceof SingleViewSection)
                {
                    current = new FragmentWithList();
                }
                break;
            case ContentTypes.PROJECTS_VOLUNTEERING:
                if (sections[position] instanceof SingleViewSection)
                {
                    current = new FragmentWithList();
                }
                break;
            case ContentTypes.CONTACTS:
                if (sections[position] instanceof SingleViewSection)
                {
                    current = new FragmentWithList();
                }
                break;
            case ContentTypes.EVENTS:
                if (sections[position] instanceof EventsScreen)
                {
                    current = new EventsFragment();
                    arguments.putSerializable(EventsFragment.CURRENT_SCREEN_TAG, sections[position]);
                }
                break;
            case ContentTypes.BILLBOARD:
                if (sections[position] instanceof MultipleAdaptersViewSection)
                {
                    current = new BillboardFragment();
                }
                break;
            case ContentTypes.SCHEDULE:
                if (sections[position] instanceof ReferencesSection)
                {
                    current = new ScheduleFragment();
                }
                break;
            case ContentTypes.TEACHERS:
                if (sections[position] instanceof SingleViewSection)
                {
                    current = new FragmentWithList();
                }
                break;
            case ContentTypes.SETTINGS:
            {
                current = new SettingFragment();
                break;
            }
            case ContentTypes.ABOUT_US:
            {
                current = new AboutUsFragment();
                break;
            }
            case ContentTypes.ABOUT_APP:
                current = new AboutAppFragment();
                break;
        }
        current.setArguments(arguments);
    }


    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
}
