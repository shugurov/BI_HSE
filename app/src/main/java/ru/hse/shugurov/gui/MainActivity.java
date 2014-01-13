package ru.hse.shugurov.gui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ListAdapter;

import ru.hse.shugurov.CallBack;
import ru.hse.shugurov.ContentTypes;
import ru.hse.shugurov.Downloader;
import ru.hse.shugurov.R;
import ru.hse.shugurov.gui.adapters.ContactAdapter;
import ru.hse.shugurov.gui.adapters.NavigationDrawerAdapter;
import ru.hse.shugurov.gui.adapters.NewsAdapter;
import ru.hse.shugurov.gui.adapters.ProjectAdapter;
import ru.hse.shugurov.gui.placeholders.BillboardPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.EventsPlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragment;
import ru.hse.shugurov.gui.placeholders.PlaceholderFragmentWithList;
import ru.hse.shugurov.model.ApplicationStructure;
import ru.hse.shugurov.model.MultipleViewScreen;
import ru.hse.shugurov.model.Parser;
import ru.hse.shugurov.model.Section;
import ru.hse.shugurov.model.SingleViewSection;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Section[] sections;
    private PlaceholderFragment[] fragments;
    private ProgressDialog dialog;
    private PlaceholderFragment currentPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        ApplicationStructure.setContext(this);
        ApplicationStructure structure = ApplicationStructure.getStructure();
        sections = structure.getSections();
        fragments = new PlaceholderFragment[sections.length];
        for (int i = 0; i < fragments.length; i++)
        {
            fragments[i] = null;
        }
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = sections[0].getTitle();

        // Set up the drawer.
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this, sections);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), adapter);
    }

    @Override
    public void onBackPressed()
    {
        //TODO обдумать, что делать, если открыт drawer
        if (currentPlaceholder != null) //TODO а что делать, если null?
        {
            if (!currentPlaceholder.moveBack())
            {
                return;
            }
        }
        super.onBackPressed();

    }

    @Override
    public void onNavigationDrawerItemSelected(final int position)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Downloader downloader;
        FragmentChanged fragmentChanged = new FragmentChanged()
        {
            @Override
            public void setCurrentFragment(PlaceholderFragment currentFragment)
            {
                MainActivity.this.currentPlaceholder = currentFragment;
            }
        };
        switch (sections[position].getType())
        {
            case ContentTypes.NEWS:
                final PlaceholderFragmentWithList placeholderFragmentWithList = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                currentPlaceholder = placeholderFragmentWithList;
                if (sections[position] instanceof SingleViewSection)
                {
                    ListAdapter adapter = ((SingleViewSection) sections[position]).getAdapter();
                    if (adapter != null)
                    {
                        placeholderFragmentWithList.setAdapter(adapter);
                    } else
                    {
                        dialog.show();
                        downloader = new Downloader(new CallBack()
                        {
                            @Override
                            public void call(String[] results)
                            {
                                NewsAdapter adapter = null;
                                if (results != null) //TODO а что делать, если null?(
                                {
                                    adapter = new NewsAdapter(MainActivity.this, Parser.parseNews(results[0]));
                                }
                                placeholderFragmentWithList.setAdapter(adapter);
                                ((SingleViewSection) sections[position]).setAdapter(adapter);
                                dialog.cancel();
                            }
                        });
                        downloader.execute(((SingleViewSection) sections[position]).getUrl());
                    }
                }
                break;
            case ContentTypes.PROJECTS_VOLUNTEERING:
                final PlaceholderFragmentWithList placeholderFragmentWithList1 = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                currentPlaceholder = placeholderFragmentWithList1;
                if (sections[position] instanceof SingleViewSection)
                {
                    ListAdapter adapter = ((SingleViewSection) sections[position]).getAdapter();
                    if (adapter != null)
                    {
                        placeholderFragmentWithList1.setAdapter(adapter);
                    } else
                    {
                        dialog.show();
                        downloader = new Downloader(new CallBack()
                        {
                            @Override
                            public void call(String[] results)
                            {
                                ProjectAdapter adapter = null;
                                if (results != null) //TODO а что делать, если null?(
                                {
                                    adapter = new ProjectAdapter(MainActivity.this, Parser.parseProjects(results[0]));
                                }
                                placeholderFragmentWithList1.setAdapter(adapter);
                                ((SingleViewSection) sections[position]).setAdapter(adapter);
                                dialog.cancel();
                            }
                        });
                        downloader.execute(((SingleViewSection) sections[position]).getUrl());
                    }
                }
                break;
            case ContentTypes.CONTACTS:
                final PlaceholderFragmentWithList placeholderFragmentWithList2 = new PlaceholderFragmentWithList(this, fragmentChanged, sections[position], position);
                currentPlaceholder = placeholderFragmentWithList2;
                if (sections[position] instanceof SingleViewSection)
                {
                    ListAdapter adapter = ((SingleViewSection) sections[position]).getAdapter();
                    if (adapter != null)
                    {
                        placeholderFragmentWithList2.setAdapter(adapter);
                    } else
                    {
                        dialog.show();
                        downloader = new Downloader(new CallBack()
                        {
                            @Override
                            public void call(String[] results)
                            {
                                ContactAdapter adapter = null;
                                if (results != null) //TODO а что делать, если null?(
                                {
                                    adapter = new ContactAdapter(MainActivity.this, Parser.parseContacts(results[0]));
                                }
                                placeholderFragmentWithList2.setAdapter(adapter);
                                ((SingleViewSection) sections[position]).setAdapter(adapter);
                                dialog.cancel();
                            }
                        });
                        downloader.execute(((SingleViewSection) sections[position]).getUrl()); //TODO убрать этот стыд(
                    }
                }
                break;
            case ContentTypes.EVENTS:
                if (sections[position] instanceof SingleViewSection)
                {
                    final EventsPlaceholderFragment eventsPlaceholderFragment = new EventsPlaceholderFragment(this, fragmentChanged, (MultipleViewScreen) sections[position], position);
                    currentPlaceholder = eventsPlaceholderFragment;
                }
                break;
            case ContentTypes.BILLBOARD:
                if (sections[position] instanceof MultipleViewScreen)
                {
                    final BillboardPlaceholderFragment billboardPlaceholderFragment = new BillboardPlaceholderFragment(this, fragmentChanged, sections[position], position);
                }
                break;
            default:
                currentPlaceholder = new PlaceholderFragment(this, fragmentChanged, sections[position], position);
                break;
        }

        fragments[position] = currentPlaceholder;
        fragmentManager.beginTransaction().replace(R.id.container, currentPlaceholder).commit();
    }

    public void onSectionAttached(int number)
    {
        mTitle = sections[number].getTitle();
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

    public interface FragmentChanged
    {
        void setCurrentFragment(PlaceholderFragment currentFragment);
    }
}
